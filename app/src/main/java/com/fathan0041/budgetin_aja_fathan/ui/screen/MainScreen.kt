package com.fathan0041.budgetin_aja_fathan.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fathan0041.budgetin_aja_fathan.R
import com.fathan0041.budgetin_aja_fathan.model.Uang
import com.fathan0041.budgetin_aja_fathan.navigation.Screen
import com.fathan0041.budgetin_aja_fathan.ui.theme.BudgetinAja_FathanTheme
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController){
    val data = listOf(
        Uang(stringResource(R.string.money) , R.drawable.money_manage),
    )
    val index by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.about_app),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(data[index],Modifier.padding(innerPadding).padding(8.dp))
    }
}
@Composable
fun ScreenContent (uang: Uang,modifier: Modifier = Modifier){
    var label by rememberSaveable { mutableStateOf("") }
    var labelError by rememberSaveable { mutableStateOf(false) }
    var labelDisplay by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var amountError by rememberSaveable { mutableStateOf(false) }
    var duration by rememberSaveable { mutableStateOf("") }
    var durationError by rememberSaveable { mutableStateOf(false)}
    var budget by rememberSaveable { mutableFloatStateOf(0f)}
    var selectedRange by rememberSaveable { mutableStateOf("Days") }
    val context = LocalContext.current

    Column (
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = uang.imageResId),
            contentDescription = stringResource(R.string.image, uang.nama),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(132.dp)
        )
        Text(
            text = stringResource(R.string.desc_app),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = label,
            onValueChange = { label = it},
            label = {
                Text(
                    text = stringResource(R.string.label)
                )
            },
            trailingIcon = { IconPicker(labelError) },
            supportingText = { ErrorHint(labelError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it},
            label = {
                Text(
                    text = stringResource(R.string.price)
                )
            },
            leadingIcon = {
                Text(
                    text = "Rp"
                )
            },
            trailingIcon = { IconPicker(amountError) },
            supportingText = {
                Column {
                    ErrorHint(amountError)
                    if (amount.toFloatOrNull() != null) {
                        Text("Target: Rp ${formatNumber(amount.toFloat())}")
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
        ) {
            DropDown(selectedText = selectedRange, onSelectionChange = { selectedRange = it })
        }
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it},
                label = {
                    Text(
                        text = stringResource(R.string.duration)
                    )
                },
                trailingIcon = { IconPicker(durationError) },
                supportingText = { ErrorHint(durationError) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
            Button(
                onClick ={
                    labelError = (label == "" || label =="-")
                    amountError =(amount == "" || amount == "0" || amount == ".")
                    durationError =(duration == "" || duration == "0")

                    if (labelError|| amountError || durationError) return@Button
                    labelDisplay = label
                    budget = calculateBudgetByRange(amount.toFloat(), duration.toFloat(), selectedRange)

                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.count)
                )
            }
            if (budget != 0f){
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 1.dp),
                    thickness = 1.dp
                )
                Text(
                    text = stringResource(R.string.label_display, labelDisplay),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.accumulated_money,"Rp ${formatNumber(budget)}" ),
                    style = MaterialTheme.typography.titleLarge
                )
                Button(
                    onClick = { shareData(
                        context = context,
                        message = context.getString(
                            R.string.share_template,
                            label,
                            selectedRange,
                            duration,
                            amount,
                            "Rp ${formatNumber(budget)}"
                        )
                    )
                              },
                    modifier = Modifier.padding(top = 8.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(R.string.share))
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(selectedText: String, onSelectionChange: (String) -> Unit){
    val list = listOf(
        "Days",
        "Week",
        "Month",
        "Year"
    )
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
    ){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}
        ) {
            TextField(
                label = { Text(text = stringResource(R.string.range)) },
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {isExpanded = false}) {
                list.forEach{ text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {onSelectionChange(text)
                         isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

fun formatNumber(number: Float): String {
    val formatter = NumberFormat.getInstance(Locale("id", "ID"))
    return formatter.format(number)
}

@Composable
fun IconPicker(isError: Boolean){
    if(isError){
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

@Composable
fun ErrorHint (isError: Boolean){
    if (isError){
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun calculateBudgetByRange(amount: Float, duration: Float, range: String): Float {
    return when (range) {
        "Days" -> amount / duration
        "Week" -> amount / (duration * 7)
        "Month" -> amount / (duration * 30)
        "Year" -> amount / (duration * 365)
        else -> 0f
    }
}

private fun shareData(context: Context, message: String){
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) !=null){
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    BudgetinAja_FathanTheme {
        MainScreen(rememberNavController())
    }
}