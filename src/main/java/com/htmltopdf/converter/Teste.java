package com.htmltopdf.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class Teste {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		File inputHTML = new File("/home/wilson/Downloads/index.html");
		String outputPdf = "/home/wilson/Downloads/teste.pdf";
		OutputStream os = new FileOutputStream(outputPdf);
		
		
		
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		
		 PdfRendererBuilder builder = new PdfRendererBuilder();
		    builder.withUri(outputPdf);
		    builder.toStream(os);
		    builder.withW3cDocument(new W3CDom().fromJsoup(document), "/");
		    builder.run();

	}
}
