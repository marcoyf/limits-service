/**
 * 
 */
package br.tec.marco.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.tec.marco.microservices.limitsservice.bean.LimitConfiguration;

/**
 * @author marcoyf
 *
 */
@RestController
public class LimitsConfiturationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		return new LimitConfiguration(
				configuration.getMaximum(), configuration.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveConfiguration() {
		throw new RuntimeException("Not available");
	}
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(8888, 8);
	}
}
