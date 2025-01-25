package hello;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@RestController
@RequestMapping("/api")
public class Controller {
	  @PostMapping("/calculate")
	    public Map<String, String> calculateExpression(@RequestParam("expression") String expression) {
	        Map<String, String> response = new HashMap<>();
	        try {
	            // Обчислення виразу
	            Expression exp = new ExpressionBuilder(expression).build();
	            double result = exp.evaluate();
	            response.put("result", String.valueOf(result));
	        } catch (Exception e) {
	            response.put("error", "Invalid expression!");
	        }
	        return response;
	    }
}
