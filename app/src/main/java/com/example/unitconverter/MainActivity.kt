package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun UnitConverter() {
    var inputValue by remember {mutableStateOf("")}
    var outputValue by remember {mutableStateOf("")}
    var inputUnit by remember { mutableStateOf("Centimeters")}
    var outputUnit by remember { mutableStateOf("Meters")}
    var inputExpanded by remember { mutableStateOf(false)}
    var outputExpanded by remember { mutableStateOf(false)}
    val conversionFactor = remember { mutableDoubleStateOf(0.01) }
    val oConversionFactor = remember { mutableDoubleStateOf(0.01) }

    fun changeUnit(unit: String, buttonType: String) {
        if (buttonType == "input"){
            inputUnit = unit
            inputExpanded = false
        } else if (buttonType == "output") {
            outputUnit = unit
            outputExpanded = false
        }
    }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.doubleValue * 100 / oConversionFactor.doubleValue).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue ,
            onValueChange = {
                inputValue = it
            },
            label = {Text("Enter Value in $inputUnit")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            val context = LocalContext.current
            Box {
                Button(onClick = {
                    inputExpanded = true
                }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = inputExpanded, onDismissRequest = { inputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            conversionFactor.doubleValue = 0.01
                            convertUnits()
                            changeUnit("Centimeters", "input")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            conversionFactor.doubleValue = 1.00
                            convertUnits()
                            changeUnit("Meters", "input")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            conversionFactor.doubleValue = 0.3048
                            convertUnits()
                            changeUnit("Feet", "input")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            conversionFactor.doubleValue = 0.001
                            convertUnits()
                            changeUnit("Millimeters", "input")
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {
                    outputExpanded = true
                }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = outputExpanded, onDismissRequest = { outputExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") },
                        onClick = {
                            oConversionFactor.doubleValue = 0.01
                            convertUnits()
                            changeUnit("Centimeters", "output")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") },
                        onClick = {
                            oConversionFactor.doubleValue = 1.00
                            convertUnits()
                            changeUnit("Meters", "output")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") },
                        onClick = {
                            oConversionFactor.doubleValue = 0.3048
                            convertUnits()
                            changeUnit("Feet", "output")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") },
                        onClick = {
                            oConversionFactor.doubleValue = 0.001
                            convertUnits()
                            changeUnit("Millimeters", "output")
                        }
                    )
                }
            }
        }
        Text("Result: $outputValue $outputUnit", style = MaterialTheme.typography.headlineMedium)
    }
}



@Preview(showBackground = true)
@Composable
fun UnitCoverterPreview(){
    UnitConverter()
}

