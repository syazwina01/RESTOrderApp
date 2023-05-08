package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;

@Controller
public class OrderTypeMenuController {
	
	//without initialisation for DefaultURI, there will be whitelabel error for adding new order type
		//Hence, initialise variable DefaultURI with a URI link
		private String defaultURI = "http://localhost:8080/orderapp/api/ordertypes";
		
	@GetMapping("/ordertype/list")
	public String getOrderTypes(Model model){
		
		//URI get order types
		String uri ="http://localhost:8080/orderapp/api/ordertypes";
		
		//get list order types from web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(uri, OrderType[].class);
		
		//Parse JSON data to array of object
		OrderType orderTypes[]=response.getBody();
		
		//Parse array to a list object
		List<OrderType> orderTypeList = Arrays.asList(orderTypes);
		
		//Attach list to model as attribute
		model.addAttribute("orderTypes",orderTypeList);
		
		return "ordertypes";
	}
	
	/**
	 * This method will update or add an order type
	 * @param orderType
	 * @return
	 */
	@RequestMapping("/ordertype/save")
	public String updateOrderType(@ModelAttribute OrderType orderType) {
		//Create new RestTmplate
		RestTemplate restTemplate = new RestTemplate();
		
		//Create request Body
		HttpEntity<OrderType> request = new HttpEntity<OrderType>(orderType);
		
		String orderTypeResponse ="";
		
		if(orderType.getOrderTypeId()>0) {
			//block update an new order type
			//send req as PUT
			restTemplate.put(defaultURI, request, OrderType.class);
			
		}else {
			//block add an new order type
			//send req as PUT

			orderTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		
		System.out.println(orderTypeResponse);
		
		//redirect req to display a list order type
		return "redirect:/ordertype/list";
		
	}
	
	
	/**
	 * This method gets an order type
	 * @param orderTypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/ordertype/{orderTypeId}")
	public String getOrderType(@PathVariable Integer orderTypeId, Model model) {
		String pageTitle ="New Order Type";
		OrderType orderType = new OrderType();
		
		//this block get an order type to be updated
		if(orderTypeId>0) {
			
			//generate new URI and append ordertypeID to it
			String uri= defaultURI+"/"+orderTypeId;
			
			//Get an order type form the web service
			RestTemplate restTemplate = new RestTemplate();
			orderType = restTemplate.getForObject(uri,OrderType.class);
			
			//give a new title to the page
			pageTitle="Edit Order Type";
			
			
		}
		
		//Attach value to pass front end
		model.addAttribute("orderType",orderType);
		model.addAttribute("pageTitle",pageTitle);
		
		
		return "ordertypeinfo";
	}
	
	
	/**
	 * This method deletes an order type
	 * @param orderTypeId
	 * @return
	 */
	@RequestMapping("/ordertype/delete/{orderTypeId}")
	public String deleteOrderType(@PathVariable Integer orderTypeId) {
		
		//generate new URI,similar to the mapping in OrderTypeRESTController
		String uri = defaultURI +"/{orderTypeId}";
		
		//send a delete req and attach value of ordertypeid into URI
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri, Map.of("orderTypeId", Integer.toString(orderTypeId)));
		
		return "redirect:/ordertype/list";
	}
	
	
	/*
	 * @GetMapping("/ordertype/list") public ResponseEntity<String> getOrderTypes(){
	 * String uri ="http://localhost:8080/orderapp/api/ordertypes";
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); ResponseEntity<OrderType[]>
	 * response = restTemplate.getForEntity(uri, OrderType[].class);
	 * 
	 * //Parse JSON data to array of object OrderType
	 * orderTypes[]=response.getBody();
	 * 
	 * System.out.println(this.getClass().getSimpleName());
	 * System.out.println("Total records: " + orderTypes.length+"\n");
	 * 
	 * //Display records for(OrderType orderType:orderTypes) {
	 * System.out.print(orderType.getOrderTypeId()+"-");
	 * System.out.print(orderType.getCode()+"-");
	 * System.out.println(orderType.getName()); } //Postman Status String message
	 * ="Check out the message in the console"; return new
	 * ResponseEntity<>(message,HttpStatus.OK);
	 * 
	 * }
	 */

}
