package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;

@Controller
public class ProductTypeMenuController {
	//without initialisation for DefaultURI, there will be whitelabel error for adding new order type
			//Hence, initialise variable DefaultURI with a URI link
			private String defaultURI = "http://localhost:8080/orderapp/api/producttypes";
			
			@GetMapping("/producttype/list")
			public String getProductTypes(Model model){
				
				//URI for Get PRODUCT TYPE
				String uri ="http://localhost:8080/orderapp/api/producttypes";
				
				//Get a list product types from the web service
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<ProductType[]> response = restTemplate.getForEntity(uri, ProductType[].class);
				
				//Parse JSON data to array of object
				ProductType productTypes[]=response.getBody();
				
				//Parse array to a list object
				List<ProductType> productTypeList = Arrays.asList(productTypes);
				
				//Attach list to model as attribute
				model.addAttribute("productTypes",productTypeList);
				
				return "producttypes";
			}
			
			/**
			 * @param productType
			 * @return
			 */
			@RequestMapping("/producttype/save")
			public String updateProductType(@ModelAttribute ProductType productType) {
				//Create new RestTmplate
				RestTemplate restTemplate = new RestTemplate();
				
				HttpEntity<ProductType> request = new HttpEntity<ProductType>(productType);
				
				String productTypeResponse ="";
				
				if(productType.getProductTypeId()>0) {
					restTemplate.put(defaultURI, request, ProductType.class);
					
				}else {
					productTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
				}
				
				System.out.println(productTypeResponse);
				
				//redirect req to display a list product type
				return "redirect:/producttype/list";
			}
			
			
			/**
			 * @param productTypeId
			 * @param model
			 * @return
			 */
			@GetMapping("/producttype/{productTypeId}")
			public String getProductType(@PathVariable Integer productTypeId, Model model) {
				String pageTitle ="New Product Type";
				ProductType productType = new ProductType();
				
				if(productTypeId>0) {
					
					String uri= defaultURI+"/"+productTypeId;
					
					
					RestTemplate restTemplate = new RestTemplate();
					productType = restTemplate.getForObject(uri,ProductType.class);
					
					
					pageTitle="Edit Product Type";
					
					
				}
				
				//Attach value to pass front end
				model.addAttribute("productType",productType);
				model.addAttribute("pageTitle",pageTitle);
				
				
				return "producttypeinfo";
			}
			
			/**
			 * @param productTypeId
			 * @return
			 */
			@RequestMapping("/producttype/delete/{productTypeId}")
			public String deleteProductType(@PathVariable Integer productTypeId) {
				
				String uri = defaultURI +"/{productTypeId}";
				
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.delete(uri, Map.of("productTypeId", Integer.toString(productTypeId)));
				
				return "redirect:/producttype/list";
			}
}
