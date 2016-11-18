/**
 * 
 */
package com.test.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.test.service.UserLoginService;

/**
 * @author ravikant.sharma Nov 9, 2016
 */
@Path("jws")
public class JerseyWebService {

	@GET
	@Path("checkValidity/{param}")
	public boolean isUserValid(@PathParam("param") String msg) throws NumberFormatException, InterruptedException {

		boolean isValid=UserLoginService.isValid(Integer.parseInt(msg));

		return isValid;

	}

	@GET
	@Path("say/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();

	}

	@GET @POST 
	@Path("/")
	public Response info() throws MissingFileException {

		String output = "Hello from jersey !!!!!!!!!!!!!";
		// throw new MissingFileException();
		return Response.status(200).entity(output).build();

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

}