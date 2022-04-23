package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.List;

import static org.apache.http.HttpStatus.*;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreatingOrderTest {
    public OrderResponse orderResponse;
    public TrackOrder trackOrder;
    private int track;

    private final List<String> color;

    public CreatingOrderTest(List<String> color){
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object [][] getColor(){
        return new Object[][] {
                { List.of("BLACK") },
                { List.of("GREY") },
                { List.of("BLACK", "GREY") },
                { List.of() },
        };
    }

    @Before
    public void setup(){
        orderResponse = new OrderResponse();
    }

    @After
    public void tearDown(){
        orderResponse.cancellationOrder(trackOrder.getTrackOrder(track));
    }

    @Test
    @DisplayName("Creating an order")
    @Description("Создание заказа")
    public void testCreatingOrder(){
        Order order = Order.getRandom(color);
        ValidatableResponse createOrderResponse = orderResponse.createOrderResponse(order);
        createOrderResponse.statusCode(SC_CREATED);
        track = createOrderResponse.extract().path("track");
        assertTrue("Заказ не создан", track > 0);
    }
}