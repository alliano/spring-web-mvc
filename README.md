# Requirement
* Java
* Spring framework
* SpringBoot
* Servlet
  
# Spring Web MVC
Spring Web MVC(Model View Controller) adalah suatu pateren atau fitur di spring untuk mempermudah membuat java Web dengan Servlet.  
Saat kita membuat web dengan Java servlet, itu sangatlah ribet maka dari itu hadirlah sebuah java framework yang mempermudah pembuatan java web dengan nama Spring Web MVC.  
Spring Web MVC menganut pateren Model View Controller yang bisa mempermudah pengembangan Java web.  

# Dispatcher Servlet
Semua logic Spring Web Mvc, di atur oleh sebuah servlet dengan nama DispatcherServlet.  
DispatcherServlet ini adalah gerbang utama masuk keluarnya Request pada Spring Web MVC.  
  
Jadi Request akan masuk ke DispatcherServlet lalu [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html) akan mendelegasikan atau meneruskan request tersebut ke Controller.  
![DispatcherServlet-Controller](./src/main/resources/images/DispatcherServlet-Controller.jpg)

# Controller
Untuk membuat contloler di Spring Web MVC kita bisa menggunakan annotation [@Controller](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html).  
Saat kita mengunakan annotation @Controller pada class yang ingin kita jadikan Controller maka kita tidak perlu lagi menambahkan annotation @Component, karena secara otomatis Spring Container akan membuatkan Spring Bean untuk contloller tersebut.  

``` java
@Controller
public class GreetingController {
    
}
```

# RequestMapping
Pada Java Servlet saat kita ingin menambahkan Routing maka kita akan menggunaakan annotation @WebServlet.  
Namun berbeda jikalau di Spring Web Mvc, pada spring web mvc jikalau kita ingin menambahkan routing maka kita bisa menggunakan annotation @RequestMappting pada method atau class yang akan kita gunakan sebagai Controller Handler

``` java
@Controller
public class GreetingController {
 
    @RequestMapping(path = "/greet")
    public void greeting(HttpServletResponse servletResponse) throws IOException {
        servletResponse.getWriter().println("Assalamuallaikum...");
    }
}
```
**NOTE** : 
> path pada @RequerstMapping wajib diisi, karena dari path itu lah kita akan menentukan url kita seperti apa. 

# Menjalankan Web
Spring Web MVC secara default mengembbed atau menambahkan Apache Tomcat sebagai Web Server.  
Maka dari itu kita tidak perlu lagi mempacage Applicaksi spring boot kita menajadi WAR file dan mendeploy nya di Apache tomcat.  
  
Secara default Spring Web MVC akan menggunakan port 8080 untuk menjalankan Apache Tomcat nya, jika kita ingin menggubah port nya maka kita bisa menggubahnya melalui `application.properties`

``` properties
server.port = 8091
```
Dan untuk menjalankanya kita bisa menjalankan perintah sebagai berikut :
``` bash
mvn spring-boot:run
```

Dan kita bisa mengakses controller kita tadi `http://localhost:8091/greet`

# HttpServletRequest & HttpServletResponse
Saat kita membuat controller method dan kita beri annotasi @RequestMapping, maka kita bisa menambahkan HttpServletRequest dan HttpServletResponse dan spring akan melakukan injection pada HttpServletRequest dan HttpServletResponse.  

``` java
@Controller
public class GreetingController {
 
    @RequestMapping(path = "/greet")
    public void greeting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Objects.nonNull(request.getParameter("name"))) response.getWriter().println("Assalamuallaikum ya ".concat(request.getParameter("name")));
        else
        response.getWriter().println("Assalmuallikum Brouther..");
    }
}
```

# MockMvc
Spring Web Mvc secara defaut menyediakan fitur Mocking yang bernama MockMvc, nah..  
MockMvc ini sangat membantu kita jikalau kita ingin melakukan testing terhadap controller kita dan sebagainya.  
  
Dengan menggunakan MockMvc kita tidak perlu lagi HTTP CLIENT misalnya seperti Insomnia, Postman, Web Brouser dan sebagainya untuk melakukan HTTP Request.    
Kita bisa membuat HTTP REQUEST dari MockMvc.  
  
Sebelum kita membaut MockMvc ada beberapa hal yang wajib dilakukan, diantaranya yaitu :
* Melakukan import static ModuleMockMvc
``` java
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
``` 

* Melakukan Auto Konfigurasi MockMvc, denga begitu Spring Container akan membuatkan MockMvc Bean secara otomatis.  
``` java

@AutoConfigureMockMvc // melakukan konfigurasi otomatis MockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void greetingNoParam() throws Exception {
		this.mockMvc.perform(
			get("/greet")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Assalmuallikum Brouther"))
		);
	}

	@Test
	public void greetingWithParam() throws Exception {
		String name = "Abdillah";
		this.mockMvc.perform(
			get("/greet")
			.queryParam("name", name)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Assalamuallaikum ya ".concat(name)))
		);
	}
}
```
**NOTE :**
> untuk lebih detail nya bisa kunjungi disini :
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html

> https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/server-performing-requests.html

# Intregation Test
Saat kita menggunakan MockMvc, Spring tidak akan menjalankan Application Web kita.  
Jadi yang dilakukan spring hanyalah menyediakan mock Request dan Mock Response.  
  
Melakuakn Testing pada Pada appliaksi web denga keadaan server web menyala, aktivitas tersebut disebut **INTREGATION TESTING**.  
Integation Testing ini artinya kita menjalankan applikasi web kita secara seutuhnya, Server, Database, dan sebagainya harus nyala.  
  
Saat kita menggunakan Spring Web Mvc, selain kita memiliki fitur MockMvc kita juga diberikan [TestRestTemplate](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html) dan [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html).  
  
Dengan menggunakan TestRestTemplate kita bisa menjalankan applikasi web kita secara otomatis saat melakukan intregation testing dan akan mematikan applikasi web kita ketika intregation testing selesai.

Untuk melakukan intregation testing, berikut ini hal-hal yang wajib dilakukan :
* Seting up port yang akan digunakan untuk intregation test
``` java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGreetingControllerIntgrationtest { }
```
*NOTE* : *webEnvironment pada annotation @SpringBootTest diunakan untuk menentukan port yang akan diguanakan untuk intregation test*

Berikut ini 2 jenis port yang sering digunakan :
| Port          | description
|---------------|--------------
| RANDOM_PORT   | Spring akan mengenerate random port
| DEFINE_PORT   | Spring akan menggunakan port yang sudah didefine di `application.properties`

Untuk lebih detail nya bisa kunjungi disini :
https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/context/SpringBootTest.WebEnvironment.html


* Mengambil port dari webEnvironment  
Untuk mengambil port dari `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` kita bisa menggunakan annotation @LocalServerPort.  

``` java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGreetingControllerIntgrationtest {
    
    private @LocalServerPort Integer port;
}
```
* setelah itu kita bisa menggunakan [TestRestTemplate](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/web/client/TestRestTemplate.html) untuk melakukan Intregation Testing

``` java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestGreetingControllerIntgrationtest {
    
    private @LocalServerPort Integer port;

    private @Autowired TestRestTemplate testRestTemplate;

    private static final String HOST = "http://127.0.0.1";

    @Test
    public void testGreetingNoParam() {
        String url = HOST.concat(":"+String.valueOf(port)).concat("/greet");
        ResponseEntity<String> response = this.testRestTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Assalmuallikum Brouther..", response.getBody().trim());
    }

    @Test
    public void terGreetingWithParam() {
        String url = HOST.concat(":".concat(String.valueOf(port))).concat("/greet?name=Abdillah");
        ResponseEntity<String> response = this.testRestTemplate.getForEntity(url, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Assalamuallaikum ya Abdillah", response.getBody().trim());
    }
}
```

# Service Layer
Pada bahasa pemograman atau framework lain, biasanya kode yang menangani bisnis logic akan dijadikan 1 dengan Controller.  
Berbeda dengan Java Programmer, Java Programmer kebanyakan memisahkan Controller dan Bisnis logic nya.  
Controller akan berfokus pada Routing dan Bisnislogic biasanya akan di handle pada Service layer.  
  
Sebenarnya hal tersebut adalah best practice di bahasa pemograman JAVA.  
Untuk membuat service layer pada spring framework, kita bisa menggunakan annotation @Service pada class.  
Dengan begitu class yang di annotasi sebagai [@Service](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html) akan dijadikan Spring bean oleh Sprng container.

``` java
public interface GreetingService {
    
    public String greet(String name);
}
```

``` java
@Service // annotation khusus untuk service layer
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
       if(name == null) return "Assalamuallikum Brouther...";
       else
       return "Assalamuallikum ya ".concat(name);
    } 
}
```

``` java
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired GreetingService gretingService;

	@Test
	public void testGreetingService() {
		String name = "Abdillah";
		String greet1 = this.gretingService.greet(null);
		String greet2 = this.gretingService.greet(name);
		Assertions.assertArrayEquals(new String[]{"Assalamuallikum Brouther...", "Assalamuallikum ya ".concat(name)}, new String[]{greet1, greet2});
	}
}
```
# Mockito
Spring framework juga menyediakan secara default Mockito, untuk melakukan mocking menggunakan mockito di Spring Web MVC sangatlah mudah, kita hanya perlu menggunakan annotatsi [@MockBean](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/mock/mockito/MockBean.html) kepada object yang ingin kita mocking, maka secara otomatis Spring container akan meregistrasikan Object tersebut sebagai Spring Bean sehingga ketika object tersebut di butuhkan maka object yang diberikan adalah object mock nya(Object tiruan).  

***NOTE*** : *unutk pembahasan Mockito telah kita bahas disini https://github.com/alliano/Junit-jupiter?tab=readme-ov-file#pengenalan-mocking*


``` java
@AutoConfigureMockMvc
@SpringBootTest(classes = SpringWebMvcApplication.class)
public class GrettinngControllerMockingTest {
    
    @MockBean
    private GreetingService greetingService;

    private @Autowired MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        /**
         * Memberikan beavior atau prilaku ketika method greet() milik greetingService dipanggil
         * 
         * pada kasus ini ketika greet() dengan parameter string apapun maka akan mengembalikan 
         * String Hello Gues
         * */
        Mockito.when(this.greetingService.greet(Mockito.anyString())).thenReturn("Hello Guest");

    }

    @Test
    public void testGreting() throws Exception {
        this.mockMvc.perform(
            get("/greet")
            .queryParam("name", "Alliano")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Hello Guest"))
        );
    } 
}
```

# Request Method
Saat kita membuat Routing dengan menggunakan @RequestMapping, terdapat attribut method milik @RequestMapping untuk menentukan Http method yang diperbolehkan untuk mengakses endpoin tersebut.  
  
Jikalau kita tidak definisikan Http Method nya maka endpoin tersebut dapat di akses dengan semua Http Method.  

Unutuk best practice nya, tiap endpoin kita harus definisikan Http Metod apa yang diizinkan untuk mengakses endpoi tersebut.  
  
Dan ketika Http Methd tersbut tidak diizinkan maka Spring akan menolak request tersebut dengan status code 405 Method Not Allowed
``` java
@Controller @AllArgsConstructor
public class GreetingController {
 
    private final GreetingService greetingService;

    // endpoin ini haya bisa di akses dengan HTTP Method GET
    @RequestMapping(path = "/greet", method = RequestMethod.GET)
    public void greeting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(this.greetingService.greet(request.getParameter("name")));
    }
}
```

Daripada kita menggunakan annotation @RequestMapping Sebenarnya terdapat annotation shortcut untuk melakukan penentuan Http Method yang diperbolehkan pada endpoin, berikut ini adalah shortcut nya :
| RequestMapping Method | Shortcut Annotation
|-----------------------|----------------------
| GET                   | @GetMapping
| POST                  | @PostMapping
| DELETE                | @DeleteMapping
| PUT                   | @PutMapping
| PATCH                 | @PatchMapping
  
Kita juga bisa melakukan kombinasi @RequestMapping dengan semua shortcut Http Method
``` java
@Controller @RequestMapping(path = "/user")
public class UserController {

    @GetMapping(path = "/hello")
    public void helloUser(HttpServletResponse response) throws IOException {
        response.getWriter().println("Hello User...");
    }

    @PostMapping(path = "/save")
    public void save(HttpServletResponse response) throws IOException{
        response.getWriter().println("SAVING.......");
    }
}
```
``` java

@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testUserController() throws Exception {
		this.mockMvc.perform(
			get("/user/hello")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Hello User..."))
		);

		this.mockMvc.perform(
			post("/user/save")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("SAVING......."))
		);
	}
}
```

# Request Param
Ketika kita menggunakan Servlet, jika kita membutuhkan request parameter kita selalu mengambill request parameter tersebut dari HttpServletRequest dengan method getParameter(nama_parameter).  
Untuk di Spring Web Mvc kita bisa menggunakan annnotation [@RequestParam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html) untuk mendapatkan request parameter.  
  
Keuntungan menggunakan @RequestParam, kita bisa menentukan apakah parameter wajib dikirimkan atau tidak dan juga kita bisa membuat default value dari parameter tidak dikirimkan.  

``` java
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    @GetMapping(path = "/test")
    // value = "name", wajib diisi, karena disinilah kita akan mengabil parameter dengan nama name
    // required = false, ini artinya parameter name tidak wajib dikirimkan
    public void test(@RequestParam(value = "name", required = false) String name, HttpServletResponse response) throws IOException {
        response.getWriter().println(name);
    }
}
```

``` java
@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testRequestParam() throws Exception {
		this.mockMvc.perform(
			get("/user/test")
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Abdillah"))
		);
	}
}
```

## Converter\<S, T>
Salhsatu fitur yang sangat menarik yang dimiliki @RequestParam adalah Converter.  
@RequestParam dapat melakukan konversi secara otomatis, dan jika kita inigin menambahkan custom converter maka kita hanya butuh mengimplementasikan interface [Converter\<S, T>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html)

``` java
@Component @Slf4j
public class DateConverter implements Converter<String, Date> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");

    @Override
    public Date convert(String source) {
        try {
            return this.simpleDateFormat.parse(source);
        } catch (Exception e) {
            log.info("Failled convert Strig to date "+e.getMessage());
            return null;
        }
    }
}
```

Setelah kita mengimplementasikanya, maka kita bisa secara langsung menggunakanya
``` java
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy mm dd");

    @GetMapping(path = "/date")
    public void dateConverter(@RequestParam(value = "date", required = false) Date date, HttpServletResponse response) throws IOException {
        response.getWriter().println("Date : "+ simpleDateFormat.format(date));
    }
}
```

``` java
@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testConvertDateFromReqParam() throws Exception {
		this.mockMvc.perform(
			get("/user/date")
			.queryParam("date", "3-4-2003")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Date : 2003 04 03"))
		);
	}
}
```

# @ResponseBody
Saat kita ingin mengembalikan Response kita selalu menggunakan `HttpServletResponse` dengan menggunakan metod `getWriter().println()`, sebenarnya saat kita menggunakan Spring Web Mvc ada cara yang lebih praktis, yaitu dengan menggunakan annotation [@ResponseBody](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseBody.html) dengan ketentukan method nya harus mereturnkan data nya.  

``` java
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy mm dd");

    @GetMapping(path = "/date") @ResponseBody
    public String dateConverter(@RequestParam(value = "date", required = false) Date date, HttpServletResponse response) throws IOException {
        return "Date : "+ simpleDateFormat.format(date);
    }
}
```

# Request Content Type
Kita bisa membatas controller yang kita buat hanya menerima content dengan tipe yang sudah ditentukan, misalnya :
* APPLICATION FORM URLENCODED VALUE
* APPLICATION JSON VALUE
* dan sebagainya

Request content Type nya tidak diizinkan maka spring akan secara otomatis menolak requs tersebut. Misalnya controller hello hanya meneriam data string namun request memberikan data selain string maka secara otomatis request tersebut akan ditolak.  
  
Kita bisa membatasi Content Type dengan menamahkan @RequestMapping atau shotcut Request mapping dengan attribut `consume`.  
``` java
@Controller
public class FormControler {
    
    @ResponseBody
    @GetMapping(path = "/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String form(@RequestParam(value = "data") String data){
        return data;
    }
}
```

``` java
@AutoConfigureMockMvc // melakukan konfigurasi otomatis MockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testRequestContentType() throws Exception {
		this.mockMvc.perform(
			get("/form")
			.queryParam("data", "some data")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("some data"))
		);
	}
}
```

# Response Content Type
Kita telah mengetahui bahwa dengan annotation `@RequestMapping` atau shortcut request method kita bisa membatasi ContentType yang boleh di terima oelh controller, selain itu juga annotation `@RequestMapping` atau shortcut request method bisa digunakan untuk membatasi ContentType yang diperbolehkan untuk dikembalikan. Caranya cukup mudah kita hanya menambahkan attribut `produces`.

``` java
@Controller
public class FormControler {

    @GetMapping(path = "/html/hello", produces = MediaType.TEXT_HTML_VALUE) @ResponseBody
    public String helloHtml(@RequestParam(value = "name") String name) {
        return """
                <html>
                    <body>
                        <h1>Hello My name is $name</h1>
                    </body>
                </html>
                """.replace("$name", name);
    }
}
```

``` java
@AutoConfigureMockMvc
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

    public void testContentProduceType() throws Exception{
		this.mockMvc.perform(
			get("/html/hello")
			.contentType(MediaType.TEXT_HTML)
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Hello My name is Abdillah"))
		);
	}
}
```

# @RequestHeader
Ketika kita mengguakan Servlet, untuk mengambil header kita menggunakan `HttpServletRequest` dengan menthod `getHeder(headerName)`, namun pada Spring Web Mvc kita dapat melakukanya dengan lebih mudah dan simpel.  

kita hanya perlu memberikan annotation `@RequestHeder` pada Controller Method, dan keuntungan menggunakan [@RequestHeader](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestHeader.html) kita bisa menentukan bahwa header tersebut wajib dikirimkan atau tidak dan juga kita bisa menentukan default value untuk header tersebut, selain itu juga annotation `@RequestHeader` mendung `Converter<S, T>`
 
``` java
@Controller
public class FormControler {
 
    @PostMapping(path = "/auth") @ResponseBody
    public String requestHeader(@RequestHeader(value = "TOKEN-API", required = true) String token) {
        return token;
    }
}
```

``` java
@AutoConfigureMockMvc
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testRequestHeader() throws Exception {
		String token = UUID.randomUUID().toString();
		this.mockMvc.perform(
			post("/auth")
			.header("TOKEN-API", token)
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString(token))
		);
	}
}
```

# @PathVariable
Path variable adalah fitur milik Spring Web Mvc yang digunakan untuk megambil value dari URL pateren.  
Dengan adanya `@PathVaiable` kita bisa membuat url yang dinamis dan juga bisa digunakan untuk mengambil value yang dinamis dari URL.  

Penggunaan annotation `@PathVariable` sangatlah mudah, kita hanya perlu meletakan annotation tersebut pada parameter method controller.  
`@PathVariable` juga mendukung `Converter<S, T>`

``` java
@Controller @RequestMapping(path = "/user")
public class UserController {

    @GetMapping(path = "/{userId}/addresses/{addressId}") @ResponseBody
    public String address(@PathVariable(value = "userId") String userId, @PathVariable(value = "addressId") String addressId){
        return "userID : ".concat(userId+"\n").concat("addressId : ").concat(addressId);
    }
}
```

``` java
@AutoConfigureMockMvc
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testPathVariable() throws Exception {
		this.mockMvc.perform(
			get("/user/".concat("alliano/addresses/").concat("addressId"))
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("userID : alliano\naddressId : addressId"))
		);
	}
}
```

# Upload File
Untuk melakukan Upload file di Spring Web Mvc bisa menggunakan `HttpServletRequest` dengan method `getPart()` seperti di java [Servlet](https://github.com/alliano/Java-Servlet?tab=readme-ov-file#upload-file).  
Namun di Srping Web Mvc terdapat cara yang lebih mudah dan simpel, yaitu dengan menggunakan annotation `@RequestPart` dengan `MultipartFile` pada argument method controller.  
``` java
@Controller @RequestMapping(path = "/file")
public class FileManagerController {
    
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) @ResponseBody
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "name") String name) throws IllegalStateException, IOException {
        Path part = Path.of("upload/"+file.getOriginalFilename());
        file.transferTo(part);
        return "Success upload file";
    }
}
```

``` java
@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testFileMangerController() throws IOException, Exception {
		this.mockMvc.perform(
			multipart("/file/upload")
			.file(new MockMultipartFile("file", "profile.png", "image/png", getClass().getResourceAsStream("/images/DispatcherServlet-Controller.jpg")))
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.queryParam("name", "Abdillah")
		).andExpectAll(
			status().isOk(),
			content().string(Matchers.containsString("Success upload file"))
		);
	}
}
```

Spring Web Mvc memiliki properties untuk mengatur kriteria file yang boleh di upload, misalnya file dengan ukuran tertentu, file dengan ektensi tertentu dan sebagainya.  
Semua pengaturan tersebut kita busa tambahkan di `application.properties` dengan prefix `spring.servlet.multipart`

contoh :
``` properties
<!-- artinya upload file dizinkan -->
spring.servlet.multipart.enabled= true
<!-- artinya maksimal ukuran file yang boleh di upload 1MB -->
spring.servlet.multipart.max-file-size= 1MB
```

**NOTE :** *Unutk lebih detailny bisa kunjungin disini : https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.web.spring.servlet.multipart.enabled*

# Request Response Body
Saat kita ingin membuat RestFull API dengan Sring Web Mvc tentunya kita akan selalu berurusan dengan Request dan Response Body, karena di Response Body ini lah API melakukan Komunikasi.

Untuk mengambil Request Body di Spring Web Mvc dari HttpRequest, kita bisa menggunakan annotation `@RequestBody` pada parameter contrller.  

``` java
@AllArgsConstructor
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy mm dd");
    
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/reqJson", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
    public String reqJson(@RequestBody String userRequest) throws JsonMappingException, JsonProcessingException {
        UserRequest userReq = this.objectMapper.readValue(userRequest, UserRequest.class);
        return this.objectMapper.writeValueAsString(UserResponse.builder()
            .name(userReq.getName())
            .email(userReq.getEmail())
            .date(this.simpleDateFormat.format(new Date(System.currentTimeMillis())))
            .build());
    }
}
```

``` java
@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	private @Autowired ObjectMapper objectMapper;

	@Test
	public void testReqResJson() throws Exception {
		UserRequest request = UserRequest.builder()
			.name("Abdillah")
			.email("unkown@gmail.com")
			.build();
		
		this.mockMvc.perform(
			post("/user/reqJson")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(this.objectMapper.writeValueAsString(request))
		).andExpectAll(
			status().isOk(),
			header().string(HttpHeaders.CONTENT_TYPE, Matchers.containsString(MediaType.APPLICATION_JSON_VALUE))
		).andDo(result -> {
			UserResponse response = this.objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
			Assertions.assertNotNull(response);
			Assertions.assertEquals("Abdillah", response.getName());
			Assertions.assertEquals("unkown@gmail.com", response.getEmail());
			Assertions.assertNotNull(response.getDate());
		});
	}
}
```

# @ResponseStatus
Saat kita membuat controller, ketika controller tersebut di panggil maka contoller akan memberikan response.  Response dari controller tersebut memiliki `HTTP STATUS` yang berfungsi sebagai indikasi terjadi galat pada system atau tidak.  
  
By Default Response Status dari controller adalah `200` yang artinya tidak ada masalah atau `OK`, Jika kita ingin Response status nya itu dinamis maka kita bisa menggunakan [`ResponseEntity<T>`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html) atau [`HttpServletResponse`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/server/ServletServerHttpResponse.html).  

Atau jikalau controller kita udah fix tidak akan meberikan Response Status tertentu maka kita bisa menggunakan [`@ResponseStatus`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html)

``` java
@AllArgsConstructor
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    @DeleteMapping(path = "/{id}") @ResponseStatus(code = HttpStatus.ACCEPTED) @ResponseBody
    public String delete(@PathVariable(value = "id") String id) {
        return "Success Deleted!";
    }
}
```

``` java
@AutoConfigureMockMvc
@SpringBootTest 
class SpringWebMvcApplicationTests {

	private @Autowired MockMvc mockMvc;

	@Test
	public void testResponseStatus() throws Exception {
		this.mockMvc.perform(
			delete("/user/238hs12y7")
		).andExpectAll(
			status().isAccepted(),
			content().string(Matchers.containsString("Success Deleted!"))
		);
	}
}
```

# ResponseEntity\<T>
Kita sebemunya telah menyinggung tentang [`ResponseEntity<T>`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html) untuk membuat response status yang dinamis.  

Nah...Untuk melakukan hal tersebut menggunakan `ResponseEntity\<T>` sangatlah mudah, kita hanya perlu melakukan return dengan type `ResponseEntity\<T>` pada method controller nya.  
Kita bisa menentukan Response Statusnya pada saat melakukan return dengan menggunakan method `status()` milik `ResponseEntity\<T>`
``` java
@Controller @RequestMapping(path = "/auth")
public class AuthenticationController {
    
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        if (email.equals("abdillah@gmail.com") && password.equals("scret"))
            return ResponseEntity.status(HttpStatus.OK).body("Success Authenticated!");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    @Test
    public void testLoginSuccess() throws Exception {
        this.mockMvc.perform(
            post("/auth/login")
            .queryParam("email", "abdillah@gmail.com")
            .queryParam("password", "scret")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Success Authenticated!"))
        );
    }

    @Test
    public void testLoginFaill() throws Exception {
        this.mockMvc.perform(
            post("/auth/login")
            .queryParam("email", "wrong@gmail.com")
            .queryParam("password", "unkown")
        ).andExpectAll(
            status().isUnauthorized(),
            content().string(Matchers.containsString("Unauthorized!"))
        );
    }
}
```

# Cookie
Di Spring web mvc saat kita ingin menambahkan cookie kita bisa menggunakan `HttpServleteResponse` dengan method `addCookie()` dan jikalau kita ingin mengambil cookie tersebut kita juga bisa menggunakan `HttpServletRequest`, namun ada cara yang lebih praktis dan mudah, yaitu dengan menggunakan annotation [`@CookieValue()`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/CookieValue.html) pada parameter method controller.

``` java
@Controller @RequestMapping(path = "/auth")
public class AuthenticationController {
    
    @PostMapping(path = "/addCookie")
    public ResponseEntity<?> setCookie(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletResponse response) {
        if (username.equals("test") && password.equals("pass")) {
            Cookie cookie = new Cookie("username", username);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.OK).body("Success Authenticated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
        }
    }

    @GetMapping(path = "/user")
    public ResponseEntity<?> getCookie(@CookieValue(name = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(username);
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    @Test
    public void testCookie() throws Exception {
        this.mockMvc.perform(
            post("/auth/addCookie")
            .queryParam("username", "test")
            .queryParam("password", "pass")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Success Authenticated!")),
            cookie().value("username", "test")

        );

        this.mockMvc.perform(
            get("/auth/user")
            .cookie(new Cookie("username", "test"))
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("test"))
        );
    }   
}
```

# @ModelAttribute
Saat kita bekerja dengan input form pada HTML, sering kali kita akan membutuhkan multiple form pada halaman html dan untuk menghandle multiple form tersebut pada controller, biasanya kita akan menggunakan annotation `@RequestParam` namun hal tersebut sangatlah buruk karena form kita memiliki banyak input dan tentunya jikalau kita menggunakan annotation `@RequestParam` maka method controller kita akan memiliki banyak parameter(sejumlah dengan input form).  
``` java
@PostMapping(path = "/user/input", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public ResponseEntity<?> formInput(@RequestParam(name = "firstName") String fristName, @RequestParam(name = "lastName") String lastName, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password, @RequestParam(name = "age") String age) {
    // logic here
}
```


Nah....Daripada menggunakan annotation `@RequestParam` lebih baik kita menggunakan annotation [`@ModelAttribute`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ModelAttribute.html)  
Cara menggunakan annotation `@ModelAttribut` ini sangatlah mudah, kita hanya perlu membuatkan object yang merepresentasikan input form dan kita gunakan object tersebut pada parameter method controller lalu kita berikan annotation `@ModelAttribute`, maka secara otomatis spring akan melakukan mapping pada object tesebut sesuai dengan input form.
  
Berikut ini class yang merepresentasikan Input form
``` java
@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {
    
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String age;

    private String brithDate;
}
```

Controller

``` java
@Controller @RequestMapping(path = "/auth") @AllArgsConstructor
public class AuthenticationController {
    
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) @ResponseBody
    public ResponseEntity<String> register(@ModelAttribute RegisterRequest registerRequest) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.objectMapper.writeValueAsString(registerRequest));
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void formRequestTest() throws Exception {
        final String firstName = "Alli";
        final String lastName = "Abdillah";
        final String email = "alli@gmail.com";
        final String password = "secret";
        final String age = "20";
        final String brithDate = "4-3-2003";
        this.mockMvc.perform(
            post("/auth/register")
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("email", email)
            .param("password", password)
            .param("age", age)
            .param("brithDate", brithDate)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpectAll(
            status().isCreated()
        ).andDo(result -> {
            RegisterRequest req = this.objectMapper.readValue(result.getResponse().getContentAsString(), RegisterRequest.class);
            Assertions.assertNotNull(req);
            Assertions.assertNotNull(req.getAge());
            Assertions.assertNotNull(req.getFirstName());
            Assertions.assertNotNull(req.getLastName());
            Assertions.assertNotNull(req.getEmail());
            Assertions.assertNotNull(req.getPassword());
            Assertions.assertNotNull(req.getBrithDate());
            Assertions.assertEquals(firstName, req.getFirstName());
            Assertions.assertEquals(lastName, req.getLastName());
            Assertions.assertEquals(email, req.getEmail());
            Assertions.assertEquals(password, req.getPassword());
            Assertions.assertEquals(brithDate, req.getBrithDate());
            Assertions.assertEquals(age, req.getAge());
        });
    }
}
```

# JSON Serialized and Deseralized
Spring Web Mvc sudah terintregasi dengan Jakson liberary dengan stabil untuk menghandle tipe data JSON. 
Jadi saat kita mendapatkan Request dan akan melakukan response pada method controller, kita tidak perlu melakukan konversi manual baik dari Request ataupun Response, semua itu sudah di handle oleh Jakson liberary, yang harus kita lakukan hanyalah memberi tahu annotation request method bahwa method tersebut akan meneriama dan mengembalikan JSON.  

``` java
@AllArgsConstructor
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@RequestBody UserRequest request) throws JsonMappingException, JsonProcessingException {
        UserResponse response = this.objectMapper.readValue(this.objectMapper.writeValueAsString(request), UserResponse.class);
        response.setDate("12-12-2023");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testJson() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                    .name("Abdillah")
                    .email("abdillah@gmail.com")
                    .build();
        String json = this.objectMapper.writeValueAsString(userRequest);
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            UserResponse response = this.objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Abdillah", response.getName());
            Assertions.assertEquals("abdillah@gmail.com", response.getEmail());
            Assertions.assertEquals("12-12-2023", response.getDate());
        });
    }
}
```

**NOTE :** *Dan juga kita bisa melakukan konfigurasi Jakson liberary di `application.properties`, untuk prefix konfigurasi jakson lib nya adalah `spring.jakson.`, untuk lebih detailnya bisa kunjungi disini : https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.json.spring.jackson.datatype.enum*

# Bean Validation
Salah satu fitur yang sanagat menarik di bahasa pemograman java adalah bean validation, dengan bean validation kita tidak perlu melakukan validasi secara manual lagi dan tentunya kode kita menjadi clean dan modular.  

Berita baiknya Spring framework telah terintregasi stabil dengan bean validation, jadi kita bisa menggunakan fitur-fitur yang dimiliki bean validation di spring framework.  

``` java
@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UserRequest {
    
    @NotBlank // annotation milik bean validation
    private String name;

    @NotBlank @Email // annotation milik bean validation
    private String email;
}
```

``` java
    @PostMapping(path = "/get", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@RequestBody @Valid UserRequest request) throws JsonMappingException, JsonProcessingException {
        UserResponse response = this.objectMapper.readValue(this.objectMapper.writeValueAsString(request), UserResponse.class);
        response.setDate("12-12-2023");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testBeanvalidationFail() throws JsonProcessingException, Exception {
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(new UserRequest()))
        ).andExpectAll(
            status().isBadRequest()
        );
    }
}
```
> untuk detail dari bean validation bisa kunjungi link ini https://github.com/alliano/java-bean-validation
> dan untuk intregasi dengan spring framework bisa kunjungi link ini https://github.com/alliano/spring-validation

# Global Exception Handler
Spring memiliki annotation yang sangat menarik, yaitu [`@ControllerAdvice`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html) yang digunakan untuk menghadle global exception, jadi jikalau terjadi exception di layer controller atau layer service atau layer repository, dansebagainya kita bisa handle dengan annotation `@ControllerAdvice`.  
Cara menggunakan `@ControllerAdvice` ini cukup sederhana kita cukup membuat class lalu kita beri annotation `@ControllerAdvice`.  
``` java
@ControllerAdvice
public class ErrorController { }
```
Dan untuk menentukan Exception jenis apa yang akan di tangkap oleh `@ControllerAdvice` kita bisa menggunakan annotation [`@ExceptionHandler`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html) dan jikalau kita ingin memodifikasi Exception nya kita bisa masukan exception tersebut sebagai parameter method.  
``` java
@ControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violation Exception : "+methodArgumentNotValidException.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> constraintVionationException(ConstraintViolationException constraintViolationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violation Exception : "+constraintViolationException.getMessage());
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testControllerAdvice() throws JsonProcessingException, Exception {
        this.mockMvc.perform(
            post("/user/get")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(new UserRequest()))
        ).andExpectAll(
            status().isBadRequest(),
            content().string(Matchers.containsString("Violation Exception :"))
        );
    }
}
```

# BindingResult
Saat terjadi validation error pada method controller, maka Bean Validation akan meng throw [`MethodArgumentNotValidException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/MethodArgumentNotValidException.html) dan request akan dihentikan.  
  
Di kasus tertentu kadang kita ingin menghandle error tersebut secara manual, misalnya jikalau terjadi validation error maka akan menampilkan halaman tertentu dan sebagainya. Hal tersebut dapat kita lakukan dengan memanfaatkan [`BindingResult`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindingResult.html) sebagai parameter method controller, Dengan begitu maka Spring akan melakukan injection kepada `BindingResult` dan object error kan dimasukan ke `BindingResult`. Setelah itu kita bisa gunakan object `BindingResult` untuk melakukan error handling secara manual.  
``` java
@AllArgsConstructor
@Controller @RequestMapping(path = "/user")
public class UserController {
    
    private final ObjectMapper objectMapper;

    @PostMapping(path = "/bindingResult")
    public ResponseEntity<?> bindingResult(@RequestBody @Valid UserRequest userRequest, BindingResult bindingResult){
       List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
               return err.getField().concat(" : ").concat(err.getDefaultMessage());
              }).collect(Collectors.toList());
         return ResponseEntity.badRequest().body(errors);
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testBindingResult() throws JsonProcessingException, Exception{
        this.mockMvc.perform(
            post("/user/bindingResult")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(new UserRequest()))
        ).andExpectAll(
            status().isBadRequest()
        ).andDo(result -> {
            List<String> response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});
            Assertions.assertNotNull(response);
            Assertions.assertEquals(2, response.size());
            response.forEach(err -> System.out.println(err));
        });
    }
}
```

# Session Attribute
Untuk membuat Session di Sring Web Mvc sangatlah mudah, yaitu dengan menggunakan `HttpServletRequest`, untuk detal ini bisa lihat disini :  
https://github.com/alliano/Java-Servlet?tab=readme-ov-file#httpsession-methods  

Dan untuk mengabil session nya kita juga bisa menggunakan `HttpServletRequest`, namn ada cara yang lebih mudah yang disediakan oleh Spring Web Mvc, yaitu menggunakan annotation [`@SessionAttribute`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/SessionAttribute.html)
``` java
@Controller @RequestMapping(path = "/auth") @AllArgsConstructor
public class AuthenticationController {
    
    private final ObjectMapper objectMapper;

    // Membuat Session
    @GetMapping(path = "/session", produces = MediaType.APPLICATION_JSON_VALUE) @ResponseBody
    public String createSession(HttpServletRequest request) {
        User user = User.builder()
                    .username("Abdillah")
                    .role("user")
                    .build();
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        return "success create session...";
    }

    // Mengambil session menggunakan annotation @SessionAttribute
    @GetMapping(path = "/session/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSession(@SessionAttribute(value = "user") User user) {
        return ResponseEntity.ok().body(user);
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTest {
    
    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;
  
    @Test
    public void testCreateSession() throws Exception{
        this.mockMvc.perform(
            get("/auth/session")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("success create session..."))
        );
    }

    @Test
    public void testGetSession() throws Exception{
        this.mockMvc.perform(
            get("/auth/session/current")
            .sessionAttr("user", User.builder().username("Abdillah").role("user").build())
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(
            result -> {
                User response = this.objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
                Assertions.assertNotNull(response);
                Assertions.assertEquals("Abdillah", response.getUsername());
                Assertions.assertEquals("user", response.getRole());
            }
        );
    }
}
```
# Web Mvc Configuration
Saat kita bekerja menggunakan Spring Web Mvc, seringkali kita akan membutuhkan konfigurasi pada applikasi kita, misalnya konfigurasi middleware, dan sebagainya. 
Untuk melakukan konfigurasi di Spring Web Mvc, kita bisa melakukanya dengan mengimplementasikan interface [`WebMvcCongigurer`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html)  

``` java
@Configuration @AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer { }
```
`WebMvcConfigurer` memiliki banyak method yang bisa kita gunakan untuk melakukan konfigurasi.

# Interceptor
Saat kita ingin melakukan filter di Java servlet kita bisa menggunakan annotation [`@WebFilter`](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/servlet/annotation/WebFilter.html) dan melakukan extend [`HttpFilter`](https://tomcat.apache.org/tomcat-9.0-doc/servletapi/javax/servlet/http/HttpFilter.html).  
Namun saat kita bekerja dengan Sring Web Mvc ada cara yang lebih baik untuk melakukan Filter, yaitu menggunakan `Interceptor`. Interceptor ini mirip seperti Filter ataupun Middleware. Cara poenggunaanya pun cukup mudah, kita hanya perlu mengimplementasikan `HandlerInterceptor` dan meregistrasikan impelentasi tersebut di `WebMvcConfigurer` menggunakan method `addInterceptors(InterceptorRegistry)`.

``` java
@Component
public class HeaderInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-API-TOKEN");
        if(Objects.isNull(token) && checToken(token)) {
            response.sendRedirect("/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean checToken(String token) {
        if(!token.startsWith("AUTH-")) return false;
        else
        return true;
    }
}
```
``` java
@Configuration @AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    
    private final HeaderInterceptor headerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerInterceptor)
            // addPathPatterens() digunakan untuk memberitahu interceptor ini nanti akan digunakan di url mana
            .addPathPatterns("/home/**");
    }
}
```
**NOTE :** *Untuk detail dari pateren patch matcher bisa kunjungi disini : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html*
``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    @Test
    public void interceptorTestFaillv() throws Exception{
        this.mockMvc.perform(
            get("/home/current")
        ).andExpectAll(
            status().is3xxRedirection()
        );
    }


    @Test
    public void testInterceptorSuccess() throws Exception{
        this.mockMvc.perform(
            get("/home/current")
            .header("username", "Abdillah")
            .header("X-API-TOKEN", "AUTH-d39hd732dh27ydr723t")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("Abdillah"))
        );
    }
}
```

# Method Argument Resolver
Sebelumnya kita pernam menggunakan annotation `@ModalAttribute`, `@RequestParam` dan sebagainya di parameter method controller.  
Pernah gak sih kalian berfikir :  
  

**YOU :** "Bijir kira-kira cara mapping object yang dianotasi `@ModalAttribute` gimanayak🤔🤔".  

**YOU :** "Hemm...:v gimana yak caranya parameter metohod controller bisa dapetin value hanya dengan diberi annotasi `@RequestParam`🤔🤔"  
  
Okay, disini saya akan menjawab : Jadi semua hal tersebut dilakukan oleh `HandlerMethodArgumentResolver`. `HandlerMethodArgumentResolver` ini memiliki 2 method utama untuk melakukan hal tersebut `supportsParameter()` dan `resolveParameter()`.  
`supportsParameter()` digunakan untuk menentukan tipe parameter apa yang akan di resolve dan `supportsParameter()` digunakan untuk logic bagaimana meresolve argument tersebut.  
  
Kita juga bisa membuat `ArgumentResolver` untuk parameter method controller yang kita inginkan. Caranya dengan mengimplementasikan `HandlerMethodArgumentResolver` dan meregistrasikanya di `WebMvcConfigurer` dengan method `addArgumentResolvers()`

``` java
@Builder
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class AuthenticationRequest {
    
    private String username;

    private String role;
}
```

``` java
@Component
public class AuthenticationMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthenticationRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = ((HttpServletRequest)webRequest.getNativeRequest());
        String token = servletRequest.getHeader("AUTH-TOKEN");
        if(!Objects.isNull(token)) {
            return AuthenticationRequest.builder()
                .username("Abdillah")
                .role("Admin")
                .build();
        }
        else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
        }
    }
}
```

``` java
@Configuration @AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticationMethodArgumentResolver authenticationMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(authenticationMethodArgumentResolver);
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testMethodArgumentResolver() throws Exception{
        this.mockMvc.perform(
            get("/auth/resource")
            .header("AUTH-TOKEN", "AUTH-ceihfr8y230r082ud023ud802u38")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            AuthenticationRequest response = this.objectMapper.readValue(result.getResponse().getContentAsString(), AuthenticationRequest.class);
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Abdillah", response.getUsername());
        });
    }

    @Test
    public void testMethodArgumnentResolverFaill() throws Exception{
        this.mockMvc.perform(
            get("/auth/resource")
        ).andExpectAll(
            status().isUnauthorized()
        );
    }
}
```

# View
Spring Web Mvc by default tidak memiliki tempalting engine untuk menghandle View, namun Spring Web Mvc telah terintregasi secara setabil dengan banyak template engine, diantaranya yaitu :
* [JSP](https://docs.oracle.com/cd/E13222_01/wls/docs81/jsp/intro.html)
* [Thymeleaf](https://www.thymeleaf.org/documentation.html)
* [Mustache](https://github.com/spullara/mustache.java)
* [FreeMarker](https://freemarker.apache.org/docs/dgui_quickstart.html)
* dan masih banyak lagi

Okay, disini kita akan mencoba membuat view dengan menggunakan Mustache.  
Sebelum kita menggunakan mustache, kita tambahkan terlebih dahulu dependency nya.  
``` xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mustache</artifactId>
</dependency>
```

Setelah itu kita pelu mengatur extension yang akan dibaca oleh mustache compiler.
``` properties
# ini artinya file yang extension nya .html akan di baca oleh mustache
spring.mustache.suffix=.html
# ini artinnya semua file tempalte engine nya disimpan di resources/tempaltes/ 
spring.mustache.prefix=classpath:/templates/
``` 
untuk detal konfigurasi mustache di spring, kita bisa lihat disini https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.templating.spring.mustache.charset
  

# ModelAndView
Untuk menampilkan view, kita bisa mereturnkan object [`ModelAndView`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/ModelAndView.html) pada controller nya.  
Dalam object `ModalView` kita bisa memasukan data template yang akan ditampilkan di tempalte view.  
  
**Example usage :**  
  
Disini kita kan membuat 1 file template yang bernama `home.html` di `resources/templates` 
``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>{{title}}</title>
</head>
<body>
    <h1>Spring web Mvc</h1>
    <p>{{content}}</p>
</body>
</html>
```
**NOTE:** 
> sintax {{key}} digunakan untuk mengambil data yang dikirimkan oleh `ModelAndView`

Setelah itu, mari kita membuat controller, dan di controler tersebut kita akan mengembalikan object `ModelAndView` besertda data yang ingin kita tampilkan di template.  

``` java
@Controller @RequestMapping(path = "/view")
public class ViewController {
    
    @GetMapping(path = "/home/{name}")
    public ModelAndView home(@RequestParam (value = "name") String name) {
        // parameter constractor yang pertama adalah nama file template kita, dan parameter ke 2 adalah key dan value data yang dikirimkan ke template
        return new ModelAndView("home", Map.of("title", "Belajar Mustache", "name", name, "content", "Hallo, ".concat(name).concat(" selamat belajar Srping web mvc dan mustache")));
    }
}
```
  
Setelah itu ketika kita akses url `http://localhost:8080/view/home?name=Abdillah` maka halaman yang tampil akan seperti ini  
![mustache](./src/main/resources/images/mustache.png)  
  
Kita juga bisa menguji dengan Unit Test.  
``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    @Test
    public void testView() throws Exception{
        this.mockMvc.perform(
            get("/view/home/abdillah")
        ).andExpectAll(
            status().isOk(),
            content().string(Matchers.containsString("abdillah")),
            content().string(Matchers.containsString("selamat belajar Srping web mvc dan mustache"))
        );
    }
}
```

# Redirect
Untuk melakukan redirect kita bisa menggunakan `HttpServletResponse` dengan method `sendRedirect(path)`, namun ketika kita menggunakan `ModelAndView` kita tidak perlu lagi menggunakan `HttpServletResponse`, kita bisa langsung mengembalikan object `ModelAndView` dengan parameter constactor `redirect:/path/to/page`

``` java
@Controller @RequestMapping(path = "/view")
public class ViewController {
    
    @GetMapping(path = "/home")
    public ModelAndView home(@RequestParam(value = "name", required = false) String name) {
        // parameter constractor yang pertama adalah nama file template kita, dan parameter ke 2 adalah key dan value data yang dikirimkan ke template
        return new ModelAndView("home", Map.of("title", "Belajar Mustache", "name", name, "content", "Hallo, ".concat(name).concat(" selamat belajar Srping web mvc dan mustache")));
    }

    @GetMapping(path = "/hello")
    public ModelAndView test(@RequestParam(value = "name", required = false) String name) {
        // contoh melakukan redirect dengan ModelAndView
        if(Objects.isNull(name)) return new ModelAndView("redirect:/view/home?name=tamu");
            else
        return new ModelAndView("hello", Map.of("title", "hello page", "name", name));
    }
}
```
Maka, ketika kita akses `http://localhost:8080/view/hello` tampa query parameter maka akan di redirect ke halaman `/home`  

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    @Test
    public void testRedirectModelAndView() throws Exception{
        this.mockMvc.perform(
            get("/view/hello")
        ).andExpectAll(
            status().is3xxRedirection()
        );
    }
}
```

# @RestController
Sebelumnya ketika kita ingin membuat controller kita selalu menggunakan annotation `@Controller`, namun ketika kita ingin membuat RestfullApi, daripada menggunakan `@Controller` lebih baik kita menggunakan `@RestController` dengan begitu tiap-tipa method controller tidak perlu lagi di berikan annotation `@ResponseBody`, karena Spring Web Mvc akan selalu mengganggap semua return value dari method controller akan diangap sebagai response Body.  

``` java
@RestController
public class WishlistController {
    
    private final List<String> wishlist = new ArrayList<String>();

    @GetMapping(path = "/wishlist")
    public List<String> addTodo(@RequestParam(value = "wishlist", required = true) String wishlist) {
        this.wishlist.add(wishlist);
        return List.of(wishlist);
    }

    @GetMapping(path = "/wishlists")
    public List<String> getAllTodo() {
        return this.wishlist;
    }
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired MockMvc mockMvc;

    private @Autowired ObjectMapper objectMapper;

    @Test
    public void testWishlistController() throws Exception{
        this.mockMvc.perform(
            get("/wishlist")
            .queryParam("wishlist", "Haji tahun 2024")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isOk()
        ).andDo(result -> {
            List<String> response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<String>>(){});
            Assertions.assertNotNull(response);
            Assertions.assertEquals("Haji tahun 2024", response.get(0));
        });
    }
}
```

# RestClient
Saat applikasi Spring Web Mvc kita sudah sangat besar dan kompleks, ada kalanya Applikasi kita butuh terhubung dengan thirdparty Api lainya, misalnya Payment Gateway, dan sebagainya.  
Untungnya Spring Web Mvc telah menyediakan standar atau cara untuk berkomunikasi dengan thirdparty Api dengan menggunakan `RestTemplate`. 

RestTemplate ini bisa kita gunakan sebagai RestClient, atau sebagai pengirim HttpRequest ke Thirdparty Api.  
Pada bahasa pemograman Java sebenarnya terdapat banyak sekali RestClient yang bisa kita gunakan, namun yang sangat populer saat ini yaitu OpenFeign dan RestTemplate. Disini kita akan mencoba keduanya.  
Untuk detal dari keduanya bisa kunjungi disini :  
* [OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
* [RestTemplate](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)

# OpenFeign
OpenFeign adalah salahsatu deklaratif Restclient yang sangat populer di kalangan java developer.  
Spring Framwork secara default tidak menambahkan OpenFeign, maka dari itu kita perlu menambahkanya dependency nya terlebih dahulu:  
``` xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Setelah itu kita perlu meng aktifkan openfeign di main class.  
``` java
@SpringBootApplication @EnableFeignClients // mengaktifkan openfeign
public class SpringWebMvcApplication {

    public static void main(String[] args) {
		SpringApplication.run(SpringWebMvcApplication.class, args);
	}
}
``` 

Setelah mengaktifkan openfeign, kita bisa membuat deklaratif RestClient dengan cara membuat interface dan diannotasi dengan [`@FeignClient`](https://www.javadoc.io/doc/org.springframework.cloud/spring-cloud-netflix-core/1.3.4.RELEASE/org/springframework/cloud/netflix/feign/EnableFeignClients.html).  
  
Pada annotasi `@FeignClient` terdapat property yang harus kita isi, yaitu `url` untuk menentukan url RestApi yang akan di consume.  

``` java
@FeignClient(url = "http://localhost:8080/", name = "wishlistFeign")
public interface WishlistFeign { }
```
**NOTE:**
> untuk property name, ini diguakan untuk memberi nama client ini


Setelah itu kita bisa mendeklarasikan, method request nya
``` java
@FeignClient(url = "http://localhost:8080/", name = "wishlistFeign")
public interface WishlistFeign {

    @GetMapping(path = "/wishlist")
    /**
     * Untuk response nya kita bisa buatkan object yang mengcover result dari request
     * namun dalam kasus ini result dari Requst kita adalah object List<String>
     * maka kita busa langsung menggunakanya
     * */
    public ResponseEntity<List<String>> addWishlist(@RequestParam(value = "wishlist", required = true) String wishlist);
    
}
```

``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {

    private @Autowired WishlistFeign wishlistFeign;

    @Test
    public void wishlistFeign(){
        ResponseEntity<List<String>> wishlist = this.wishlistFeign.addWishlist("Berangkat haji tahun 2024");
        Assertions.assertNotNull(wishlist);
        Assertions.assertEquals(HttpStatus.OK, wishlist.getStatusCode());
        List<String> body = wishlist.getBody();
        Assertions.assertEquals("Berangkat haji tahun 2024", body.get(0));
    }

    @Test
    public void getWishlistFeign(){
        this.wishlistFeign.addWishlist("Berangkat haji tahun 2024");
        this.wishlistFeign.addWishlist("Jalan Jalan ke Madinah tahun 2024");
        this.wishlistFeign.addWishlist("Nikah tahun 2024");
        this.wishlistFeign.addWishlist("freedom financila di tahun 2030");
        ResponseEntity<List<String>> result = this.wishlistFeign.getWishlists();
        Assertions.assertTrue(result.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(!result.getBody().isEmpty());
    }

}
```

**NOTE:** *Untuk detail dari OpenFeign, kita akan bahas di chapter terpisah*

# RestTemplate
Spring Web Mvc by default sudah include RestTemplate, jadi kita tidak perlu menambahkan dependency untuk RestTemplate lagi.  
  
Untuk menggunakan RestTemplate, kita harus membuat Bean RestTemplate nya terlebih dahulu. Kita buisa membuat Bean RestTemplate dari Object Bean RestTemplateBuilder(Udah disediakan oleh Spring)
``` java
@Configuration
public class ApplicationConfiguration {
    
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(2L))
            .setReadTimeout(Duration.ofSeconds(2L))
            .build();
    }
}
```

Setalah itu kita bisa menggunakan RestTemplate untuk RestClient, RestTemplate memiliki banyak sekali method yang bisa kita gunakan, untuk detail nya bisa kunjungi disini https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
``` java
@SpringBootTest(classes = SpringWebMvcApplication.class) @AutoConfigureMockMvc
public class ApplicationTes2 {
    
    private @Autowired RestTemplate restTemplate;

    @Test
    public void testRestTemplate(){
        String url = "http://localhost:8080/wishlist?wishlist=jalan%20ke%20swish";
        RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET, URI.create(url));
        ResponseEntity<List<String>> response = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
        Assertions.assertNotNull(response);
        Assertions.assertEquals("jalan ke swish", response.getBody().get(0));
    }

    @Test
    public void getWishlistRestTemplate(){
        URI url = URI.create("http://localhost:8080/wishlists");
        RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET, url);
        ResponseEntity<List<String>> response = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<String>>(){});
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}
```

# Error Page
Saat terjadi error, misalnya :  
* Internal Server error 
* Resource Not Found
* RuntimeException
* dan sebagainya

Spring akan menampilkan detail error tersebut pada browser,  
![errorPage](./src/main/resources/images/pageerr.png)  
 saat applikasi spring kita sudah selesai di develop dan akan masuk ke mode production lebih baik error yang terjadi pada system applikasi spring jangan di tampilkan di browser, karena error tersebut bisa dimanfaatkan oleh pihak yang tidak bertanggung jawab.  
  
By default saat terjadi error spring akan membaca path url `/error`, jika path url `/error` tidak ada maka spring akan menampilkan default error page di browser.  
  
Untuk melakukan custom error page di Spring Web Mvc, kita harus mengimplementasik interface [`ErrorController`](https://docs.spring.io/spring-boot/docs/2.0.0.RELEASE/api/org/springframework/boot/web/servlet/error/ErrorController.html)   
Setelah itu kita buatkan sebuah controller dengan path url `/error`, setelah membuat path url `/error` kita harus menonaktifkan default error page pada `application.properties`
``` properties
server.error.whitelabel.enabled=false
```
  
Misalnya disini saya memiliki file html yang disimpan di `resources/pages`  
![error](./src/main/resources/images/errorPage.png) 
``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p>Status : $StatusCode</p>
    <p>Message : $Message</p>
</body>
</html>
``` 
Yangmana file tersebut akan dibaca oleh error custom contrroler yang kita buat ketika Error terjadi.

``` java
@Controller
public class ErrorPageCustomController implements ErrorController {
    
    @RequestMapping(path = "/error") @SneakyThrows
    public ResponseEntity<?> error(HttpServletRequest request) {
        Optional<Object> statusCode = Optional.ofNullable(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        String errorMessage = (String)request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        File file = ResourceUtils.getFile("classpath:pages/errorPage.html");
        String raw = new String(Files.readAllBytes(Path.of(file.getPath())));
        String html = raw.replace("$StatusCode", statusCode.get().toString()).replace("$Message", errorMessage);
        return ResponseEntity.status((Integer)statusCode.get()).body(html);
    }
}
```
Maka ketika terjadi error, yang di tampilkan adalah custom error yang telah kita buat  
![errorCustom](/src/main/resources/images/errorCustom.png)





