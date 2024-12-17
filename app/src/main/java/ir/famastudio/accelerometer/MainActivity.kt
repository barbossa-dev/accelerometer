package ir.famastudio.accelerometer

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.famastudio.accelerometer.ui.theme.AccelerometerTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    private val x = mutableStateOf("")
    private val y = mutableStateOf("")
    private val z = mutableStateOf("")
    private lateinit var sensorManager: SensorManager
    private lateinit var acc: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        enableEdgeToEdge()
        setContent {
            AccelerometerTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(220.dp)
                                .padding(10.dp),
                            painter = painterResource(id = R.drawable.ic_barbossa),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Barbossa",
                            color = Color.White,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily(
                                Font(R.font.font)
                            )
                        )
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "x:${x.value}", color = Color.White)
                            Text(text = "y:${y.value}", color = Color.White)
                            Text(text = "z:${z.value}", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        sensorManager.unregisterListener(this)
        super.onStop()
    }

    @SuppressLint("DefaultLocale")
    override fun onSensorChanged(p0: SensorEvent?) {
        x.value = String.format("%.2f", p0?.values?.getOrNull(0) ?: 0)
        y.value = String.format("%.2f", p0?.values?.getOrNull(1) ?: 0)
        z.value = String.format("%.2f", p0?.values?.getOrNull(2) ?: 0)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}
