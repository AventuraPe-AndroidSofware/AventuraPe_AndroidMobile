package com.example.aventurape_androidmobile.domains.adventurer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aventurape_androidmobile.Beans.Adventure
import com.example.aventurape_androidmobile.R
import com.example.aventurape_androidmobile.domains.adventurer.screens.states.AdventurerHomeViewModel
import com.example.aventurape_androidmobile.ui.theme.Brown
import com.example.aventurape_androidmobile.ui.theme.Cream

@Composable
fun AdventurerHomeScreen(viewModel: AdventurerHomeViewModel, navController: NavController){
    val dataSeed: List<Adventure> = listOf(
        Adventure(
            id = 6,
            entrepreneurId = 6,
            nameActivity = "Crear un blog",
            description = "Start a personal or niche blog",
            timeDuration = 150,
            image = "https://images.pexels.com/photos/4778424/pexels-photo-4778424.jpeg",
            cantPeople = 1,
            cost = 50.00F
        ),
        Adventure(
            id = 7,
            entrepreneurId = 7,
            nameActivity = "Vender productos en línea",
            description = "Sell products through an online platform",
            timeDuration = 60,
            image = "https://images.pexels.com/photos/4778424/pexels-photo-4778424.jpeg",
            cantPeople = 4,
            cost = 200.00F
        ),
        Adventure(
            id = 8,
            entrepreneurId = 8,
            nameActivity = "Organizar eventos",
            description = "Plan and organize events such as parties or conferences",
            timeDuration = 300,
            image = "https://images.pexels.com/photos/4778424/pexels-photo-4778424.jpeg",
            cantPeople = 20,
            cost = 110.00F
        ),
        Adventure(
            id = 9,
            entrepreneurId = 9,
            nameActivity = "Crear una tienda física",
            description = "Establish a physical retail store",
            timeDuration = 180,
            image = "https://images.pexels.com/photos/4778424/pexels-photo-4778424.jpeg",
            cantPeople = 6,
            cost = 100.00F
        ),
        Adventure(
            id = 10,
            entrepreneurId = 10,
            nameActivity = "Ofrecer clases particulares",
            description = "Provide private tutoring in a specific subject",
            timeDuration = 120,
            image = "https://images.pexels.com/photos/4778424/pexels-photo-4778424.jpeg",
            cantPeople = 2,
            cost = 100.00F
        ))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(
            id= R.drawable.ic_brand),
            contentDescription = "AdventureRape Brand Icon",
            colorFilter = ColorFilter.tint(Cream),
            modifier = Modifier
                .width(180.dp)
                .height(40.dp)
                .align(Alignment.Start)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(30.dp), color = Cream)
        LocationsSection()
        Spacer(modifier = Modifier.height(20.dp))
        AdventuresSection(dataSeed)
        Spacer(modifier = Modifier.height(10.dp))
        Text("¿Estás listo para dejarte llevar?",fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(270.dp)
            , onClick = {  }
            , colors = ButtonDefaults.buttonColors(Brown)
        ) {
            Text("SORPRÉNDEME", fontSize = 25.sp)
        }
    }
}
@Composable
fun LocationsSection(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.20f)
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Locations", fontSize = 20.sp, fontWeight = FontWeight(600))
    }
}

@Composable
fun AdventuresSection(adventures: List<Adventure>){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.60f)
            .padding(1.dp)
    )
    {
        Text("Adventures", fontSize = 20.sp, fontWeight = FontWeight(600))
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            items(adventures.size) {
                    index -> val post = adventures[index]
                CardItem(200.dp,180.dp, post)
            }


        }
    }
}
@Composable
fun CardItem(with: Dp, height: Dp, adventure: Adventure){
    Card(
        modifier = Modifier
            .height(height)
            .width(with)
            .padding(0.dp)
        ,
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(5.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .clickable {  }
            ){
                AsyncImage(
                    model = adventure.image,
                    contentDescription = "adventure image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${adventure.cost}",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 6.dp, end = 6.dp)
                        .background(Brown, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 15.dp, vertical = 2.dp)
                    ,
                    fontSize = 12.sp

                )
            }
            Column ( modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .padding(4.dp)
            )
            {
                Text(adventure.nameActivity,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(600),
                    color = Brown)
                Spacer(modifier = Modifier.height(2.dp))
                Text(adventure.description,
                    fontSize = 12.sp, color = Brown,
                    lineHeight = 14.4.sp, overflow = TextOverflow.Ellipsis,
                    maxLines = 2)
            }
        }
    }

}

