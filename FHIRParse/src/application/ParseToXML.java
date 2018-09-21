package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class ParseToXML {

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws IOException {
		String inputfile = args[0];// input filename
		String outputfile = args[1];// output path
		
		BufferedReader br;
		String line;
		StringBuilder sb;
		br = new BufferedReader(new FileReader(new File(inputfile)));
		
		File f = new File(inputfile);
		String inputFilename = f.getName();
		String fileNameWithOutExt = FilenameUtils.removeExtension(inputFilename);
		
		sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		String result = sb.toString();

		FhirContext ctx = FhirContext.forDstu3();
		IParser parser = ctx.newJsonParser();
		// resource = parser.parseResource(rr, result);
		Object resource = parser.parseResource(result);
		String outputJSON = ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString((IBaseResource) resource);

		try {
			// output the string into the output location
			BufferedWriter bw = null;
			FileWriter fw = null;

			//String fileName = new SimpleDateFormat("yyyyMMddHHmm'.xml'").format(new Date());
			String fileName = fileNameWithOutExt+".xml";

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