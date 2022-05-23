package com.indieml.mltext

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.indieml.mltext.ml.Cars
import com.indieml.mltext.ui.theme.MltextTheme
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

//import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MltextTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }

        }
    }
}
private fun Predict(context: Context, cylinders: String, displacement: String,
                    hp: String, weight: String, acceleration: String, year:String, country:String): Float {

    var europe =  if (country == "Europe") 1F else 0F
    var japan =  if (country == "Japan") 1F else 0F
    var usa =  if (country == "USA") 1F else 0F

    val model = Cars.newInstance(context)
// Creates inputs for reference.
    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 9), DataType.FLOAT32)
    inputFeature0.loadArray(floatArrayOf(cylinders.toFloat(),
        displacement.toFloat(),
       hp.toFloat(),weight.toFloat(),
        acceleration.toFloat(),year.toFloat(),europe,japan,usa))
// Runs model inference and gets result.
    val outputs = model.process(inputFeature0)
    val outputFeature0 = outputs.outputFeature0AsTensorBuffer
    val pred = outputFeature0.floatArray
    model.close()
    return pred[0]
}


@Composable
fun Greeting(name: String) {
    var expanded by remember { mutableStateOf(false) }
    var prediction by remember { mutableStateOf(0F) }
    val context = LocalContext.current
    var cylinders by remember { mutableStateOf("8") }
    var displacement by remember { mutableStateOf("307") }
    var horsepower by remember { mutableStateOf("130") }
    var weightval by remember { mutableStateOf("3504") }
    var acceleration by remember { mutableStateOf("12") }
    var year by remember { mutableStateOf("70") }
    var country by remember { mutableStateOf("USA") }
    val countries = listOf("Europe","USA","Japan")
    var dropDownWidth by remember { mutableStateOf(0) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column( modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Cars", color= Color.DarkGray, fontSize = 70.sp )
            Text(

                buildAnnotatedString {
                    withStyle(style = SpanStyle( color = Color(Color.Gray.value))
                    ) {
                        append("Predict miles per gallon (MPG) for a car, based on features captured in the data sciences cars dataset. ")
                    }
                    append("Predictions are made ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append("on device")
                    }
                    append(" with tensorflow lite! ")
                }
            )
        }
        if (prediction > 0F) Card(
            elevation = 10.dp , modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Text(text = String.format("%.2f", prediction), fontSize = 70.sp )
                Text(text = "MPG", fontSize = 20.sp, modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(14.dp) )
            }
            
        }

        Divider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
        Column() {
            Row() {
                OutlinedTextField( value = cylinders, onValueChange = { cylinders = it }, label = { Text("Cylinders", fontSize = 15.sp) }, modifier = Modifier
                    .padding(5.dp)
                    .weight(0.5F))
                OutlinedTextField(value = displacement, onValueChange = { displacement = it }, label = { Text("Displacement", fontSize = 15.sp) } , modifier = Modifier
                    .padding(5.dp)
                    .weight(0.7F))
                OutlinedTextField(value = horsepower, onValueChange = { horsepower = it }, label = { Text("HP", fontSize = 15.sp) } , modifier = Modifier
                    .padding(5.dp)
                    .weight(0.7F))

            }
            Row() {
                OutlinedTextField( value = weightval, onValueChange = { weightval = it }, label = { Text("Weight", fontSize = 15.sp) }, modifier = Modifier
                    .padding(5.dp)
                    .weight(0.4F))
                OutlinedTextField(value = acceleration, onValueChange = { acceleration = it }, label = { Text("Acceleration", fontSize = 15.sp) } , modifier = Modifier
                    .padding(5.dp)
                    .weight(0.7F) )
                OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Year", fontSize = 15.sp) } , modifier = Modifier
                    .padding(5.dp)
                    .weight(0.4F) )
            }
            Row() {
                OutlinedTextField(
                    value = country,
                    enabled = false,
                    onValueChange = {country = it},
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .onSizeChanged { dropDownWidth = it.width },
                    trailingIcon = { Icon(icon,"contentDescription", Modifier.clickable { expanded = !expanded })
                    },
                    label = { Text(text = "Country", fontSize = 15.sp)}

                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(with(LocalDensity.current){dropDownWidth.toDp()})
                ) {
                    countries.forEach { label ->
                        DropdownMenuItem(onClick = {
                            country = label
                            expanded = false
                            Log.w("Model",label)
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }
        }
        ElevatedButton(
            onClick = {
            prediction = Predict(context, cylinders,displacement,horsepower,weightval,acceleration,year,country)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
        ) {
            Text("Predict ", modifier = Modifier.padding(6.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MltextTheme {
        Greeting("Android"  )
    }
}