package com.jitenbr.ecom.inventoryservice;

import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("api/v1")
public class MainRestController {

    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    AuthService authService;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    StockRepository stockRepository;

    @PostMapping("/add/stock")
    public ResponseEntity<?> addStock(@RequestBody Stock stock, @RequestHeader("Authorization") String token)
    {
        log.info("Received request to add stock: {}", stock);
        if(authService.validateToken(token) && productService.validateProduct(stock.getProductid(), token))
        {
            log.info("Token is valid: {}", token);
            stockRepository.save(stock);
            log.info("Stock added successfully: {}", stock);
            return ResponseEntity.ok("Stock added successfully");
        }
        else
        {
            log.info("Token or product is invalid: {}", token);
            return ResponseEntity.badRequest().body("Invalid token or product");
        }
    }

    @GetMapping("/get/stock/{productid}")
    public ResponseEntity<?> getStock(@PathVariable("productid") String productid)
    {
        Stock stock = null;
        if(stockRepository.findById(productid).isPresent())
        {
            stock = stockRepository.findById(productid).get();
            log.info("Stock found: {}", stock);
            return ResponseEntity.ok(stock);
        }
        else
        {
            log.info("Stock not found for productid: {}", productid);
            return ResponseEntity.ok().body(null);
        }
    }

    @PostMapping("/reserve/stock")
    public ResponseEntity<?> reserveStock(@RequestBody StockRequest stockRequest, @RequestHeader("Authorization") String token)
    {
        log.info("Received request to reserve stock: {}", stockRequest);
        if(authService.validateToken(token))
        {
            log.info("Token is valid: {}", token);
            Stock stock = stockRepository.findById(stockRequest.getProductid()).get();
            if(stock.getQuantity() >= stockRequest.getQuantity())
            {
                stock.setQuantity(stock.getQuantity() - stockRequest.getQuantity());
                stockRepository.save(stock);
                log.info("Stock reserved successfully: {}", stock);
                return ResponseEntity.ok("Stock reserved successfully");
            }
            else
            {
                log.info("Insufficient stock: {}", stock);
                return ResponseEntity.badRequest().body("Insufficient stock");
            }
        }
        else
        {
            log.info("Token is invalid: {}", token);
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }


}
