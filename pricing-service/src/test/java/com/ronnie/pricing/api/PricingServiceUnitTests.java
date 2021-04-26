package com.ronnie.pricing.api;

import com.ronnie.pricing.service.PricingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PricingController.class)
public class PricingServiceUnitTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    PricingService pricingService;
    
    @Test
    public void getPrice() throws Exception {
        Random random = new Random();
        long vehicleId = 1 + random.nextInt(20);
        mockMvc.perform(get("/services/price/" + vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(pricingService, times(1)).getPrice(vehicleId);
    }
}
