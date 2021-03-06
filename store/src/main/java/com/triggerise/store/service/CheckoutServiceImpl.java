
package com.triggerise.store.service;

import com.triggerise.store.domain.Checkout;
import com.triggerise.store.domain.Items;
import com.triggerise.store.domain.NewPromotion;
import com.triggerise.store.domain.Product;
import com.triggerise.store.exception.ResourceNotFoundException;
import com.triggerise.store.model.Products;
import com.triggerise.store.model.Promotions;
import com.triggerise.store.repository.ProductPromotion;
import com.triggerise.store.repository.ProductRepository;
import com.triggerise.store.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@Service
public class CheckoutServiceImpl implements CheckoutService{


    private ProductRepository productRepository;

    private PromotionRepository promotionRepository;

    private ProductPromotion productPromotion;

    @Autowired
    public CheckoutServiceImpl(ProductRepository productRepository, PromotionRepository promotionRepository, ProductPromotion productPromotion) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.productPromotion = productPromotion;
    }

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CheckoutServiceImpl.class);


    @Override
    public Products addProduct(Products products) {
        logger.info(String.valueOf(products));
        Products addProducts =new Products();
        if(products.getCode()!=null && !products.getCode().trim().equalsIgnoreCase("")){
            List<Products> findproduct = productRepository.findByCode(products.getCode());

            if(findproduct!=null &&findproduct.size()>0){
                throw new ResourceNotFoundException("Product code  already exist, product code: "+products.getCode());
            }else{
                addProducts.setCode(products.getCode());
            }
        }else {
            throw new ResourceNotFoundException("Product code is required");
        }

         if(products.getName()!=null && !products.getName().trim().equalsIgnoreCase("") ){addProducts.setName(products.getName()); }else {
             throw new ResourceNotFoundException("Product name is required");
         }

         if (products.getPrice()!=0){addProducts.setPrice(products.getPrice());}else{
             throw new ResourceNotFoundException("product price is required");
         }
       return productRepository.save(addProducts);
    }

    @Override
    public Promotions addPromotions(NewPromotion promotions){
        logger.info(String.valueOf(promotions));
        Promotions addPromotion = new Promotions();
        if(promotions.getPromotions().getDescription()!=null
                && !promotions.getPromotions().getDescription().trim().equalsIgnoreCase("")){
            addPromotion.setDescription(promotions.getPromotions().getDescription());}else{
            throw new ResourceNotFoundException("Promotion description is required");
        }
        if(promotions.getPromotions().getCode()!=null && !promotions.getPromotions().getCode().trim().equalsIgnoreCase("")){ addPromotion.setCode(promotions.getPromotions().getCode());}else{
            throw new ResourceNotFoundException("Promotion code is required");
        }
        if(promotions.getPromotions().getQuantity()!=0 ){ addPromotion.setQuantity(promotions.getPromotions().getQuantity());}else{
            throw new ResourceNotFoundException("Promotion quantity is required");
        }
        if(promotions.getPromotions().getStart_date()!=null){ addPromotion.setStart_date(promotions.getPromotions().getStart_date());
        }else{
            throw new ResourceNotFoundException("Promotion start date  is required");
        }
        if(promotions.getPromotions().getEnd_date()!=null){ addPromotion.setEnd_date(promotions.getPromotions().getEnd_date());
        }else{
            throw new ResourceNotFoundException("Promotion end date  is required");
        }
        if(promotions.getPromotions().getDiscount_percentage()==0 && promotions.getPromotions().getDiscount_amount()==0){
            throw new ResourceNotFoundException("Promotion  percentage  or discount amount is required");
        }
        if(promotions.getPromotions().getDiscount_percentage()!=0){addPromotion.setDiscount_percentage(promotions.getPromotions().getDiscount_percentage());}

        if(promotions.getPromotions().getDiscount_amount()!=0){addPromotion.setDiscount_amount(promotions.getPromotions().getDiscount_amount());}

            if(promotions.getProduct()!=null && promotions.getProduct().size()>0){

                    for (int i = 0; i < promotions.getProduct().size(); i++) {
                        List<Products> findproduct = productRepository.findByCode(promotions.getProduct().get(i).getCode());

                        if(findproduct.size()!=0){
                                if (findproduct.get(0).getPrice() > promotions.getPromotions().getDiscount_amount()) {
                                    if (findproduct.get(0).getPromotions()!=null &&findproduct.get(0).getPromotions().size()>0) {
                                        if (findproduct.get(0).getPromotions().get(0).getEnd_date().before(promotions.getPromotions().getStart_date())) {
                                            addPromotion.promotiontype(findproduct.get(0));
                                        } else {
                                            throw new ResourceNotFoundException("Product is already on Promotion");
                                        }
                                    }else{
                                        addPromotion.promotiontype(findproduct.get(0));
                                    }
                                } else {
                                    throw new ResourceNotFoundException("Promotion discount amount cannot be greater than the product price");
                                }

                        }else {
                            throw new ResourceNotFoundException("Product code does not exist");
                        }
                    }
            }else {
                throw new ResourceNotFoundException("No products added for promotion");
            }
        return promotionRepository.save(addPromotion);
    }

    @Override
    public Checkout checkoutItems(Checkout checkout){

        logger.info(String.valueOf(checkout));
        Checkout addItems=new Checkout ();

        ArrayList<Items> items = new ArrayList<Items>();
        List<String> validateDuplicates = new ArrayList<>();
        double total =0.0;
        if(checkout.getItems()!=null) {
            Map<String, List<Product>> groupByProductCode  = new HashMap<>();
            groupByProductCode = checkout.getItems().stream()
                    .collect(Collectors.groupingBy(Product::getCode));
                for (int i = 0; i <checkout.getItems().size(); i++) {
                    List<Product> pruduct=groupByProductCode.get(checkout.getItems().get(i).getCode());
                    String actualProduct=pruduct.get(0).getCode();
                    int quantity=groupByProductCode.get(checkout.getItems().get(i).getCode()).size();
                    Items item=new Items();
                    item.setProduct(actualProduct);
                    item.setQuantity(quantity);
                    if(validateDuplicates.contains(actualProduct)==false){
                        validateDuplicates.add(actualProduct);
                    items.add(item);
                    }
                }
            total+= calculatePromotion(items);
        }else{
            throw new ResourceNotFoundException("no product items were added");
        }

        checkout.setTotal(total);
        return checkout;
    }



    public double calculatePromotion(List<Items> groupByProductCode){

        logger.info(String.valueOf(groupByProductCode));
        Double total = 0.0;
        final int hundredPercent =100;
        int calculateProductTotAmount=0;
        int productTotalCost=0;
        if(groupByProductCode!=null){
            for (int c = 0; c < groupByProductCode.size();c++) {
            List<Products> findproduct = productRepository.findByCode(groupByProductCode.get(c).getProduct());
            if(findproduct.size()>0){
                Date sysDatedate=new Date();
               if(findproduct.get(0).getPromotions()!=null && findproduct.get(0).getPromotions().size()>0){
                   for (int p = 0; p < findproduct.get(0).getPromotions().size();p++) {
                       if (findproduct.get(0).getPromotions().get(p).getEnd_date().after(sysDatedate)) {
                           if(findproduct.get(0).getPromotions().get(p).getDiscount_percentage()!=0){
                               if(groupByProductCode.get(c).getQuantity()>=findproduct.get(0).getPromotions().get(p).getQuantity()){
                               int calcPercent=findproduct.get(0).getPromotions().get(p).getDiscount_percentage()/hundredPercent;
                               productTotalCost+= (int) (findproduct.get(0).getPrice() * groupByProductCode.get(c).getQuantity());
                                calculateProductTotAmount+=productTotalCost*calcPercent;
                               double productAmount=productTotalCost-calculateProductTotAmount;
                               total = total + productAmount;
                               }else{
                                   total = total +findproduct.get(0).getPrice() * groupByProductCode.get(c).getQuantity();
                               }
                           }else if (findproduct.get(0).getPromotions().get(p).getDiscount_amount()!=0.0){
                               if(groupByProductCode.get(c).getQuantity()>=findproduct.get(0).getPromotions().get(p).getQuantity()){
                                   int itemstoSubtract=groupByProductCode.get(c).getQuantity()/findproduct.get(0).getPromotions().get(p).getQuantity();
                                   int totaldiscount= (int) (findproduct.get(0).getPrice()*itemstoSubtract);
                                   productTotalCost= (int) (findproduct.get(0).getPrice() * groupByProductCode.get(c).getQuantity());
                                   total = total + productTotalCost- totaldiscount;
                               }else{
                                   total = total +findproduct.get(0).getPrice() * groupByProductCode.get(c).getQuantity();
                               }
                           }
                       }
                   }
               }else{
                   total=total+findproduct.get(0).getPrice() * groupByProductCode.get(c).getQuantity();
               }
            }else {
                throw new ResourceNotFoundException("Product code does not exist");
            }

            }

        }else {
            throw new ResourceNotFoundException("no product added");
        }
        return total;
    }


    @Override
    public List<Products> getAllProduct() {
        return this.productRepository.findAll();
    }


    @Override
    public List<Promotions> getAllPromotion() {
        return this.promotionRepository.findAll();
    }

    @Override
    public List<Products> getProductById(String code) {
        return this.productRepository.findByCode(code);
    }

}
