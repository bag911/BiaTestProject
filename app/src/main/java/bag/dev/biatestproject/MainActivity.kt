package bag.dev.biatestproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import bag.dev.biatestproject.viewmodel.TerminalViewModel
import bag.dev.biatestproject.databinding.ActivityMainBinding
import bag.dev.biatestproject.fragments.AppBarLogic
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), AppBarLogic{

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        navInit()
        checkUserLocation()
        mainBinding.toolbar.visibility = View.GONE
    }

    //Checking access of permission
    private fun checkPermission() {
        super.onResume()
        val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val checkPermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (checkPermission == PackageManager.PERMISSION_GRANTED || checkPermission2 == PackageManager.PERMISSION_GRANTED){
            return
        }
        else
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                10
            )
    }

    //Check user location and send req to get List of terminals
    private fun checkUserLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val viewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        checkPermission()
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            viewModel.getData(LatLng(location.latitude, location.longitude))
        }
        fusedLocationClient.lastLocation.addOnFailureListener {
            Toast.makeText(this, "Error on check location", Toast.LENGTH_SHORT).show()
        }
    }

    //Connect navigation
    private fun navInit() {
        setSupportActionBar(mainBinding.toolbar)
        val host = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = host.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mainBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    //Connect options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return false
    }

    override fun hide() {
        mainBinding.toolbar.visibility = View.GONE
    }

    override fun show() {
        mainBinding.toolbar.visibility = View.VISIBLE
    }
}