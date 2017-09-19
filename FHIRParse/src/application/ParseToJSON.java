package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class ParseToJSON {

	@SuppressWarnings({ "resource", "rawtypes" })
	public static void main(String[] args) throws IOException {
		String resourcetype = args[0];// type of resource
		String inputfile = args[1];// input filename
		String outputfile = args[2];// output path

		BufferedReader br;
		String line;
		StringBuilder sb;
		br = new BufferedReader(new FileReader(new File(inputfile)));
		
		
		sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		String result = sb.toString();

		FhirContext ctx = FhirContext.forDstu3();
		IParser parser = ctx.newXmlParser();
		// send the resource argument to getResource method
		Class rr = getResource(resourcetype);
		@SuppressWarnings("unchecked")
		Object resource = parser.parseResource(rr, result);

		String outputJSON = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString((IBaseResource) resource);

		try {
			// output the string into the output location
			BufferedWriter bw = null;
			FileWriter fw = null;

			String fileName = new SimpleDateFormat("yyyyMMddHHmm'.json'").format(new Date());
			
			fw = new FileWriter(outputfile+"/"+fileName);
			bw = new BufferedWriter(fw);
			bw.write(outputJSON);

			bw.close();
		} catch (IOException ioe) {

			ioe.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class getResource(String resource) {

//		String lowercase = resource.toLowerCase();
//		switch (lowercase) {
//
//		case "patient":
//			return Patient.class;
//		default: 
//			return null;
//		}
		
		return null;

	}
}