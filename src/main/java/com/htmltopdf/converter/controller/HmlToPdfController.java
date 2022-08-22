package com.htmltopdf.converter.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@RestController
@RequestMapping(value = "/html-to-pdf")
public class HmlToPdfController {
	
	@PostMapping
	public void convert(@RequestParam("file") MultipartFile file, HttpServletResponse res) throws IOException {

		File inputHTML = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(inputHTML);
		fos.write(file.getBytes());
		fos.close();
		
		OutputStream os = res.getOutputStream();
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		
		PdfRendererBuilder builder = new PdfRendererBuilder();
		builder.toStream(os);
		builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
		builder.run();

		ServletOutputStream out = res.getOutputStream();
		out.flush();
		out.close();
	}

}
