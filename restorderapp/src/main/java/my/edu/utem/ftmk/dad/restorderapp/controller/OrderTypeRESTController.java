package my.edu.utem.ftmk.dad.restorderapp.controller; 
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;
import my.edu.utem.ftmk.dad.restorderapp.repository.OrderTypeRepository; 
 
 
 
@RestController 
@RequestMapping("/api/ordertypes") 
public class OrderTypeRESTController { 
	
	
 
 @Autowired 
 private OrderTypeRepository orderTypeRepository; 
 

 
 
 @RequestMapping("/find/code")
 public List<OrderType> findOrderTypeByCode(@PathVariable OrderType orderType) {
	 
	 List<OrderType> orderTypes = 
			 orderTypeRepository.findByCodeStartingWith(orderType.getCode());
	 return orderTypes;
     // Implementation of finding order type data according to the specified name
     // Use orderTypeRepository to retrieve the data based on the name
     // Return the list of found OrderType objects
 }
 
 

  
 //For class OrderType 
  
 //retrieve order types 
 @GetMapping 
 public List<OrderType> getOrderTypes() 
 { 
  return orderTypeRepository.findAll(); 
   
 } 
  
 //retrieve order details 
 @GetMapping("{orderTypeId}") 
 public OrderType getOrderType(@PathVariable long orderTypeId) 
 { 
  OrderType orderType = orderTypeRepository.findById(orderTypeId).get(); 
   
  return orderType; 
 } 
  
 //insert order type 
 @PostMapping 
 public OrderType insertOrderType(@RequestBody OrderType orderType) 
 { 
  return orderTypeRepository.save(orderType); 
 } 
  
 //update order type 
 @PutMapping 
 public OrderType updateOrderType(@RequestBody OrderType orderType) 
 { 
  return orderTypeRepository.save(orderType); 
 } 
  
 @DeleteMapping("{orderTypeId}") 
 public ResponseEntity<HttpStatus> deleteOrderType(@PathVariable long orderTypeId) 
 { 
  orderTypeRepository.deleteById(orderTypeId); 
  return new ResponseEntity<>(HttpStatus.OK); 
 } 
 
 
 /*
	 * Lab: 11 Task 2 - Find Order Type by Name
	 * Method find order type data according to the order type's name.
	 * 
	 * @param orderType
	 * @return A list of order types record.
	 */
	@RequestMapping("/find/name")
	public List<OrderType> findOrderType (@RequestBody OrderType orderType)
	{
		List<OrderType> orderTypes = orderTypeRepository.findByNameContaining(orderType.getName());
		return orderTypes;
	}
	
	/*
	 * Lab: 11 Task 3 - Find Order Type by Code
	 * Method find order type data according to the order type's partial code.
	 * 
	 * @param partCode
	 * @return A list of order types record.
	 */
	@RequestMapping("/find/code/{partCode}")
	public List<OrderType> findOrderType(@PathVariable String partCode)
	{
		List<OrderType> orderTypes = orderTypeRepository.findByCodeStartingWith(partCode);
		return orderTypes;
	}
	
	
	/*
	 * Lab: 11 Task 4 - Count Order Type Data
	 * Method count number of order type data.
	 * 
	 * @return A long data type of number
	 */
	@RequestMapping("/count")
	public long countOrderType()
	{
		long number = orderTypeRepository.count();
		return number;
	}
	
	
	/*
	 * Lab: 11 Task 5 - Retrieve a sorted Order Type Data
	 * Method retrieves a list of order type data sorted in ascending order.
	 * 
	 * @param orderType
	 * @return A list of order type data
	 */
	@GetMapping("/asc")
	public List<OrderType> findSortedOrderTypeName()
	{
		return orderTypeRepository.findByOrderByNameAsc();
	}
	
	
	/*
	 * Lab: 11 Task 8.2
	 * This method demonstrate the invocation of custom query and return the
	 * result in Object form.
	 * 
	 * @return A list of objects where value of each field in separated arrays
	 */
	@GetMapping("/find/pickup/raw")
	public List<Object[]> getRawPickUpOrderCode(){
		
		// Execute query method
		List<Object[]> objOrderTypes = orderTypeRepository.selectCustomByCode();
		
		// For debugging purpose
		for(Object[] objOrderType: objOrderTypes) {
			
			for(Object currentObject: objOrderType) {
				
				System.out.println(currentObject.toString());
			}
		}
		
		return objOrderTypes;
	}
	
	/**
	 * Lab: 11 Task 8.4
	 * This method demonstrate the invocation of custom query
	 * 
	 * @return A list of objects where result of query execution wrap in
	 * OrderType
	 */
	@GetMapping("/find/pickup/wrap")
	public List<OrderType> getWrapPickUpOrderCode() {
		
		// Execute query method
		List<Object[]> objOrderTypes = orderTypeRepository.selectCustomByCode();
		
		// Wrap result in a list of order type
		List<OrderType> orderTypes = new ArrayList<OrderType>();
		for (Object[] objOrderType:objOrderTypes) {
			
			// Wrap in order type
			OrderType orderType = new OrderType();
			orderType.setCode(objOrderType[0].toString());
			orderType.setName(objOrderType[1].toString());
			
			// Add into list
			orderTypes.add(orderType);
		}
		
		return orderTypes;
	}
	
	
	
  
  
}