package io.lightfeather.springtemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ApiController {

    String url = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers";

    @RequestMapping("/")
    public String home() {
        return "You can access the api data feed via /api/supervisors.\nYou can access an api submission test via /api/submissiontest.";
    }
    
    @GetMapping("/api/supervisors")
    public ArrayList<String> getSupervisors() {

        ArrayList<Supervisor> supervisors = new ArrayList<Supervisor>();

        HttpResponse<String> response;

        // Compose request, and store response.
        // SPEC: "a call is made to GET https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers to pull down a list of managers."
        // Note: A list of json object instances as string.
        try{
            response = HttpClient.newHttpClient().send(HttpRequest.newBuilder().GET().uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        // Deserialize String representation of json output onto ArrayList of Supervisor
		try {
			supervisors = new ObjectMapper().readValue(response.body(), new TypeReference<ArrayList<Supervisor>>() {});
		} catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Remove Supervisor instance if jurisdiction is numeric.
        // SPEC: "Numeric jurisdictions should be excluded from the response."
        supervisors.removeIf(n -> ( n.getJurisdiction().matches("[0-9]+[\\.]?[0-9]*") ) );

        // Apply sorting
        // SPEC: Supervisors within the endpoint response payload should be sorted in alphabetical order first by jurisdiction, then by lastName and firstName.
        supervisors.sort(Comparator.comparing(Supervisor::getJurisdiction)
          .thenComparing(Comparator.comparing(Supervisor::getLastName)
          .thenComparing(Comparator.comparing(Supervisor::getFirstName))));

        // Compose list of Strings for output.
        // SPEC: A list of supervisor strings formatted as follows: “jurisdiction - lastName, firstName”
        ArrayList<String> output = new ArrayList<String>();
        for( Supervisor s : supervisors ){
            output.add(s.getJurisdiction() + " - " + s.getLastName() + ", " + s.getFirstName());
        }

        return output;
    }

    // SPEC: The endpoint should expect to be provided the following parameters by means of your preferred content type.
	@PostMapping("/api/submit")
	//public ResponseEntity<String> submit(@RequestBody Employee employee) {
    public ResponseEntity<String> submit(@RequestParam(value = "firstName") String firstName, 
                                         @RequestParam(value = "lastName") String lastName, 
                                         @RequestParam(value = "email") String email, 
                                         @RequestParam(value = "phone") String phone, 
                                         @RequestParam(value = "supervisor") String supervisor) {
        // TODO: Web parameter submission instead of POJO. DONE, retaining code for commentary purposes.
        // TODO: Spring constraints.
        // TODO: Spring common validation.
        // JDTODO: Best way to do this arg interface would be json object instance,
        //  with field of real supervisor mapped json object instance validated to exist; but NOSPEC.

        // SPEC: The submitted data above should be printed to the console upon receiving the post request.
        System.out.println(new StringBuilder().append(firstName).append(", ").
            append(lastName).append(", ").
            append(email).append(", ").
            append(phone).append(", ").
            append(supervisor));

        // SPEC: If firstName, lastName, or supervisor is not provided, the endpoint should return an error response. These are required parameters.
        if (firstName == null || lastName == null || supervisor == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("firstname, lastname, and supervisor are required.");
        }

        Employee employee = new Employee(firstName, lastName, email, phone, supervisor);
        // TODO: Business process fo rEmployee, NOSPEC.

	    return ResponseEntity.status(HttpStatus.OK).build();
        // JDTODO: figure out spring custom requests
	}

    // Simple functional test. NOSPEC
    @RequestMapping("/api/submissiontest")
    public ResponseEntity<String> submissionTest()  {
        //Employee employee = new Employee("Samuel", "Clemmins", "samc@mississippi.net", "555-555-5555",  "b - Elijah, Cremin");
        //Employee employee1 = new Employee("Mark", "Twain", null, null, "b - Elijah, Cremin");
        //Employee employee2 = new Employee("John", "Steinbeck", "jsteinbeck@centralvalley.org", "555-555-5555", null);
        //return this.submit(employee2);
        
        this.submit("Samuel", "Clemmins", "samc@mississippi.net", "555-555-5555",  "b - Elijah, Cremin");
        this.submit("Mark", "Twain", null, null, "b - Elijah, Cremin");
        return this.submit("John", "Steinbeck", "jsteinbeck@centralvalley.org", "555-555-5555", null);
    }
}