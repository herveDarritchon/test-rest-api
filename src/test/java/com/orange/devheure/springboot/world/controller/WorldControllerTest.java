/**
 * 
 */
package com.orange.devheure.springboot.world.controller;

//import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.orange.devheure.springboot.world.WorldApplication;
import com.orange.devheure.springboot.world.model.World;

/**
 * @author Hervé Darritchon
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WorldApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class WorldControllerTest {

	private static final String X_AUTH_USERNAME = "x_auth_username";
	private static final String X_AUTH_PASSWORD = "x_auth_password";
	// Will contain the random free port number
	@Value("${local.server.port}")
	private int port;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	// Some convenience methods to help you interact with your rest interface

	/**
	 * @param requestMappingUrl
	 *            should be exactly the same as defined in your RequestMapping
	 *            value attribute (including the parameters in {})
	 *            RequestMapping(value = yourRestUrl)
	 * @param serviceReturnTypeClass
	 *            should be the the return type of the service
	 * @param parametersInOrderOfAppearance
	 *            should be the parameters of the requestMappingUrl ({}) in
	 *            order of appearance
	 * @return the result of the service, or null on error
	 */
	protected <T> T getEntity(final String requestMappingUrl,
			final Class<T> serviceReturnTypeClass,
			final Object... parametersInOrderOfAppearance) {
		// Make a rest template do do the service call
		final TestRestTemplate restTemplate = new TestRestTemplate();
		// Add correct headers, none for this example
		final HttpEntity<String> requestEntity = new HttpEntity<String>(
				this.createHeaders("admin", "secret"));
		try {
			// Do a call the the url
			final ResponseEntity<T> entity = restTemplate.exchange(getBaseUrl()
					+ requestMappingUrl, HttpMethod.GET, requestEntity,
					serviceReturnTypeClass, parametersInOrderOfAppearance);
			// Return result
			System.out.println(String.format("Exit with Http Status = %s",entity.getStatusCode().toString()));
			return entity.getBody();
		} catch (final Exception ex) {
			System.out.println(ex.getStackTrace());
		}
		System.out.println("Exit with null value.");
		return null;
	}

	/**
	 * @param requestMappingUrl
	 *            should be exactly the same as defined in your RequestMapping
	 *            value attribute (including the parameters in {})
	 *            RequestMapping(value = yourRestUrl)
	 * @param serviceListReturnTypeClass
	 *            should be the the generic type of the list the service
	 *            returns, eg: List<serviceListReturnTypeClass>
	 * @param parametersInOrderOfAppearance
	 *            should be the parameters of the requestMappingUrl ({}) in
	 *            order of appearance
	 * @return the result of the service, or null on error
	 */
	protected <T> List<T> getList(final String requestMappingUrl,
			final Class<T> serviceListReturnTypeClass,
			final Object... parametersInOrderOfAppearance) {
		final ObjectMapper mapper = new ObjectMapper();
		final TestRestTemplate restTemplate = new TestRestTemplate();
		final HttpEntity<String> requestEntity = new HttpEntity<String>(
				new HttpHeaders());
		try {
			// Retrieve list
			final ResponseEntity<List> entity = restTemplate.exchange(
					getBaseUrl() + requestMappingUrl, HttpMethod.GET,
					requestEntity, List.class, parametersInOrderOfAppearance);
			final List<Map<String, String>> entries = entity.getBody();
			final List<T> returnList = new ArrayList<T>();
			for (final Map<String, String> entry : entries) {
				// Fill return list with converted objects
				returnList.add(mapper.convertValue(entry,
						serviceListReturnTypeClass));
			}
			return returnList;
		} catch (final Exception ex) {
			// Handle exceptions
		}
		return null;
	}

	/**
	 * 
	 * @param requestMappingUrl
	 *            should be exactly the same as defined in your RequestMapping
	 *            value attribute (including the parameters in {})
	 *            RequestMapping(value = yourRestUrl)
	 * @param serviceReturnTypeClass
	 *            should be the the return type of the service
	 * @param objectToPost
	 *            Object that will be posted to the url
	 * @return
	 */
	protected <T> T postEntity(final String requestMappingUrl,
			final Class<T> serviceReturnTypeClass, final Object objectToPost) {
		final TestRestTemplate restTemplate = new TestRestTemplate();
		final ObjectMapper mapper = new ObjectMapper();
		try {
			final HttpEntity<String> requestEntity = new HttpEntity<String>(
					mapper.writeValueAsString(objectToPost));
			final ResponseEntity<T> entity = restTemplate.postForEntity(
					getBaseUrl() + requestMappingUrl, requestEntity,
					serviceReturnTypeClass);
			return entity.getBody();
		} catch (final Exception ex) {
			// Handle exceptions
		}
		return null;
	}

	private HttpHeaders createHeaders(final String username, final String password ){
	    HttpHeaders headers =  new HttpHeaders(){
	          /**
			 * 
			 */
			private static final long serialVersionUID = 7001536022656253739L;

			{
	             String auth = username + ":" + password;
	             byte[] encodedAuth = Base64.encodeBase64(
	                auth.getBytes(Charset.forName("UTF-8")) );
	             String authHeader = "Basic " + new String( encodedAuth );
	             set( "Authorization", authHeader );
	          }
	       };
	       headers.add("Content-Type", "application/json");
	       headers.add("Accept", "application/json");

	       return headers;
	}
	
	/**
	 * Returns the base url for your rest interface
	 * 
	 * @return
	 */
	private String getBaseUrl() {
		return "http://localhost:" + port;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.orange.devheure.springboot.world.controller.WorldController#retreiveWorld(java.lang.Long)}
	 * .
	 */
	@Test
	public void testRetreiveWorld() {
		String resourcePath = "/worlds/1";
		World world = getEntity(resourcePath, World.class, (Object) null);
		assertTrue("should not be null", world != null);
		assertThat(world.getName(), is("Euskal Herria"));
	}

	@Test
	public void canFetchWorld() {
		int worldId = 1;
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";

		RestAssured.given().auth().preemptive().basic("admin", "secret").when()
				.get("/worlds/{id}", worldId).then()
				.statusCode(HttpStatus.SC_OK)
				.body("name", Matchers.is("Euskal Herria"))
				.body("id", Matchers.is(worldId));
	}

};
