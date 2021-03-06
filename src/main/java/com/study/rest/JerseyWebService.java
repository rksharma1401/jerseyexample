/**
 * 
 */
package com.study.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import javax.inject.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.ws.http.HTTPException;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ChunkedOutput;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.study.service.UserLoginService;
import com.study.vo.User;

import rx.functions.Action1;

/**
 * @author ravikant.sharma Nov 9, 2016
 */
@Singleton
@Path("jws")
public class JerseyWebService {

	Date dt = null;

	public JerseyWebService() {
		super();
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		dt = new Date();
		 
	}

	@GET
	@Path("getInitDate")
	public String getInitDate() {
		return dt.toString();
	}

	@GET
	@Path("testDatabase")
	public String testDatabase() throws ClassNotFoundException {
		String response = "inital";
		// Get DataSource from JNDI (defined in context.xml file)
		Context ctx;
		Class.forName("com.mysql.jdbc.Driver");
		try {
			ctx = new InitialContext();

			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySQLDS");
			Connection c = null;
			Statement s = null;

			// Get Connection and Statement from DataSource
			c = ds.getConnection();
			s = c.createStatement();
			ResultSet result = s.executeQuery("show status where `variable_name` = 'Threads_connected'");
			if (result.next())
				response = "No of Threads_connected right now : " + result.getString(2);
			s.close();
			c.close();
		} catch (NamingException e) {
			response = e.getMessage();
			e.printStackTrace();
		} catch (SQLException e) {
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}
	
	@GET
	@Path("testSQLite")
	public String testSQLite() throws ClassNotFoundException {
		String response = "inital";
		// Get DataSource from JNDI (defined in context.xml file)
		Context ctx;
		Class.forName("org.sqlite.JDBC");
		try {
			ctx = new InitialContext();

			DataSource ds = (DataSource) ctx.lookup("java:jboss/jdbc/sqlite");
			Connection c = null;
			Statement s = null;

			// Get Connection and Statement from DataSource
			c = ds.getConnection();
			s = c.createStatement();
			ResultSet result = s.executeQuery("SELECT * from users");
			if (result.next())
				response = "Data " + result.getString(1) + "         " +result.getString(2);
			s.close();
			c.close();
		} catch (NamingException e) {
			response = e.getMessage();
			e.printStackTrace();
		} catch (SQLException e) {
			response = e.getMessage();
			e.printStackTrace();
		}
		return response;
	}

	@Path("/resource")
	@GET
	public void asyncGet(@Suspended final AsyncResponse asyncResponse) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = veryExpensiveOperation();
				asyncResponse.resume(result);
			}

			private String veryExpensiveOperation() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "Done";
			}

		}).start();
	}

	static List<User> pojos = new ArrayList<User>();

	@GET
	@Path("getDataAsClient")
	@Produces("application/json")
	public Response getDataAsClient() throws InterruptedException {
		rx.Observable<Response> observable = Rx.newClient(RxObservableInvoker.class).register(new LoggingFilter())
				.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/getUserList/")
				// .target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/getUserList")
				.register(JacksonFeature.class).request().header("key", "12345").rx().get();
		observable.subscribe(new Action1<Response>() {

			@Override
			public void call(Response response) {
				try {
					System.out.println(" Inside call ");
					ObjectMapper ob = new ObjectMapper();
					synchronized (pojos) {
						pojos = ob.convertValue(response.readEntity(List.class), new TypeReference<List<User>>() {
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		Thread.sleep(10 * 1000);
		return Response.status(200).entity(pojos).build();
	}

	@GET
	@Path("toHindi/{param : .+}")

	public String translate(@PathParam("param") String msg) {
		Client client = ClientBuilder.newClient();
		String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + "en" + "&tl=" + "hi"
				+ "&dt=t&q=" + URLEncoder.encode(msg);
		client.register(new LoggingFilter());
		System.out.println(url);
		Response response = client.target(url).request().get();
		String res = response.readEntity(String.class).replaceAll(Pattern.quote("["), "")
				.replaceAll(Pattern.quote("\""), "").split(",")[0];
		return res;
	}

	@GET
	@Path("checkValidity/{param}")
	public boolean isUserValid(@PathParam("param") String id) throws NumberFormatException, InterruptedException {

		boolean isValid = UserLoginService.isValid(Integer.parseInt(id));

		return isValid;

	}

	@GET
	@Path("say/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;
		return Response.status(200).entity(output)
				/*
				 * .cookie( new NewCookie( "userAccessToken", "token", "/", "",
				 * "what is this", 3600, false ) )
				 */
				.header("Set-Cookie", "userAccessToken=toke;lang=en-US; Path=/; Domain=localhost").build();

	}

	@POST
	@Path("createUser")
	@Produces("application/json")
	@Consumes("application/json")
	public Response createUser(User u) {
		u=UserLoginService.createUser(u);
		return Response.ok(u).build();
	}
	@GET
	public Response info() {
		String output = "Hello from jersey !!!!!!!!!!!!!";
		CacheControl cc = new CacheControl();
		cc.setMaxAge(120);
		cc.setPrivate(false);
		cc.setNoStore(false);
		cc.setNoTransform(false);
		cc.setNoCache(false);
		;
		System.out.println("in info");
		ResponseBuilder builder = Response.ok(output);
		builder.cacheControl(cc);
		return builder.build();

	}

	@GET
	@Produces("application/pdf")
	@Path("getPDF")
	// public StreamingOutput streamExample() {
	public Response streamExample() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, os);
					document.open();
					document.add(new Paragraph(new Date().toString()));
					for (int i = 0; i < 99999; i++)
						document.add(new Paragraph("howtodoinjava.com"));
					document.add(new Paragraph(new Date().toString()));
					document.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		};
		// return stream;
		return Response.ok(stream).build();
	}
	@GET 
	@Produces({MediaType.MULTIPART_FORM_DATA})
	@Path("getMultiFormData")
	// public StreamingOutput streamExample() {
	public Response getMultiFormData() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, os);
					document.open();
					document.add(new Paragraph(new Date().toString()));
					for (int i = 0; i < 99999; i++)
						document.add(new Paragraph("howtodoinjava.com"));
					document.add(new Paragraph(new Date().toString()));
					document.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		};
		// return stream;
		return Response.ok(stream).build();
	}

	@GET
	@Produces("application/pdf")
	@Path("downlaodPDF")
	// public StreamingOutput streamExample() {
	public Response downlaodPDF() {
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				Document document = new Document();
				try {
					PdfWriter.getInstance(document, os);
					document.open();
					document.add(new Paragraph("howtodoinjava.com"));
					document.add(new Paragraph(new Date().toString()));
					document.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		};
		// return stream;
		// return Response.ok(stream).build();
		return Response.ok(stream).header("Content-Disposition", "attachment; filename=hwllo.pdf").build();
	}

	@GET
	@Path("getUserList")
	@Produces("application/json")
	public Response getDataNoZip() {
		ArrayList/* <study.vo.User> */ llstUser = new ArrayList<User>();
		for (int i = 0; i < 50; i++) {
			User r = new User();
			r.setCompany("wp");
			r.setPost("Devloper");
			r.setName("userName");
			llstUser.add(r);
		}
		GenericEntity<ArrayList> list = new GenericEntity<ArrayList>(llstUser) {
		};
		GenericEntity<List<User>> users = new GenericEntity<List<User>>(llstUser) {
		};
		return Response.status(200).entity(users).build();

	}

	@GET
	@Path("getListRes")
	@Produces("application/json")
	public Response getListResponse() {

		List<Response> responses = new ArrayList<>();
		responses.add(Response.ok().build());
		responses.add(Response.notModified().build());
		responses.add(Response.noContent().build());
		GenericEntity<List> list = new GenericEntity<List>(responses) {
		};
		return Response.status(200).entity(list).build();

	}

	@POST
	@Path("setListReq")
	@Produces("application/json")
	@Consumes("application/json")
	public Response receiveListRequest(ListUser lstUserData) {

		for (User lUserTemp : lstUserData) {
			System.out.println(lUserTemp.getName());
		}

		return Response.status(200).build();
	}

	/*
	 * @GET
	 * 
	 * @Path("sendlstUser/{param}") //@Consumes("application/json") public
	 * Response getListRequest(@PathParam("param") ListUser llstUserData) {
	 * 
	 * for (User lUserTemp : llstUserData) {
	 * System.out.println("getListRequest : "+ lUserTemp.getName()); }
	 * 
	 * return Response.status(200).build(); }
	 */

	@GET
	@Path("getObj/{param}")
	@Produces("application/json")
	public Response getObj(@PathParam("param") String msg) {

		ListUser responses = new ListUser();
		responses.add(new User("1"));
		responses.add(new User("2"));
		responses.add(new User("3"));
		if ("list".equals(msg))
			return Response.status(200).entity(responses).build();
		User u = new User();
		u.setName("Developer");
		return Response.status(200).entity(u).build();

	}

	@GET
	@Path("getMap")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMap() {
		Map<String, String> outputMap = new HashMap<String, String>();
		outputMap.put("1", "One");
		outputMap.put("2", "two");
		Gson gson = new Gson();
		String jsonString = gson.toJson(outputMap);
		return jsonString;

	}

	@GET
	@Path("test401withcontent")
	public Response get401TestWithContent() {
		return Response.status(401).entity("return some text").build();
	}

	@Path("streaming/{loopcount}/{sleepTime}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public ChunkedOutput<String> getChunkedStream(@PathParam("loopcount") String loopcount,
			@PathParam("sleepTime") String sleepTime) throws Exception {

		final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);
		final Integer val = Integer.parseInt(loopcount);
		final Integer isleepTime = Integer.parseInt(sleepTime) * 1000;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					StringBuffer chunk = null;

					for (int i = 0; i < 10; i++) {
						chunk = new StringBuffer();
						for (int j = 0; j < val; j++) {
							chunk.append(" Message #" + i + "#" + j);
						}
						output.write(chunk.toString() + "\r\n");
						System.out.println("write");
						Thread.sleep(isleepTime);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						System.out.println("output.close();");
						output.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}).start();

		return output;
	}

	@Path("cstreaming/{param}/{sleepTime}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public ChunkedOutput<String> getContinuosChunkedStream(@PathParam("param") String loopcount,
			@PathParam("sleepTime") String sleepTime) throws Exception {

		final ChunkedOutput<String> output = new ChunkedOutput<>(String.class);
		final Integer val = Integer.parseInt(loopcount);
		final Integer isleepTime = Integer.parseInt(sleepTime);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String chunk = null;
					int randomNum = 0;
					for (int i = 0; i < val; i++) {
						randomNum = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE - 1);
						chunk = Integer.toBinaryString(randomNum);
						output.write(chunk.toString() + "\r\n");
					}
					System.out.println("write");
					Thread.sleep(isleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						System.out.println("output.close();");
						output.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}).start();

		return output;
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("uploadFile")
	public Response newFile(@FormDataParam("file") InputStream uploadInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("description") String description) {
		try {
			String uploadFileLocation = "/var/lib/openshift/58231da67628e1e3e1000009/app-root/logs";

			OutputStream out = null;

			int read = 0;
			byte[] bytes = new byte[1024];

			File directory = new File(uploadFileLocation);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			out = new FileOutputStream(new File(directory, fileDetail.getFileName()));

			while ((read = uploadInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity("Succesfull uplaod").build();

	}

	@POST
	@Path("redirect")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response redirect(@FormDataParam("file") InputStream uploadInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("description") String description,
			@javax.ws.rs.core.Context HttpServletRequest servletRequest,
			@javax.ws.rs.core.Context HttpServletResponse servletResponse) {

		try {

			String uploadFileLocation = "/var/lib/openshift/58231da67628e1e3e1000009/app-root/logs";

			OutputStream out = null;

			int read = 0;
			byte[] bytes = new byte[1024];

			File directory = new File(uploadFileLocation);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			out = new FileOutputStream(new File(directory, fileDetail.getFileName()));

			while ((read = uploadInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			out.flush();
			out.close();

			String output = "Redirect"; 
			servletResponse.sendRedirect("http://jerseyexample-ravikant.rhcloud.com/print.jsp?output="+output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static final String FILE_PATH = "/log/file.xls";

	@GET
	@Path("/getxls")
	@Produces("application/vnd.ms-excel")
	public Response getFile() {

		File file = new File(FILE_PATH);

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=new-excel-file.xls");
		return response.build();

	}

	@GET
	@Path("/getError/{isHttp}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	public Response exceptionTester(@PathParam("isHttp") String isHttp) throws Exception {
		Boolean bIsHttp = new Boolean(isHttp);
		if (bIsHttp)
			throw new HTTPException(500);
		else
			throw new Exception("exceptionTester ErrorMessage");

	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/jsonData")
    public Response createStudent(JsonObject jsonData)     {
        // -- process data
		JsonObject json = new JsonObject();
        System.out.println(jsonData.toString());
        json.addProperty("message", "created successful");
        return Response.status(Status.OK).entity(json).type(MediaType.APPLICATION_JSON).build();
    }
 
	 

}

class ListUser extends ArrayList<User> {
	private static final long serialVersionUID = 6200328291688791710L;
}

class ListGen<T> extends ArrayList<T> {
	private static final long serialVersionUID = -2119658700477976022L;
}