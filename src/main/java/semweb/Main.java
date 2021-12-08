package semweb;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.*;

public class Main {
	
	static Model TP() {
		BufferedReader reader;
		Model m = ModelFactory.createDefaultModel();
		try {
			reader = new BufferedReader(new FileReader(
					"stops_sample.txt"));
			String line = reader.readLine();
			
			while (line != null) {
				
				// if a txt
				// split data
				String[] col = line.split(",");
				String stop_id = col[0];
				String stop_name = col[1];
				String stop_lat = col[3];
				String stop_lon = col[4];
				//System.out.println(stop_id);
				Resource id=m.createResource(stop_id);
				
				// define the prefix
				m.setNsPrefix("ex", "http://example.com/");
				m.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
				m.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
				m.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
				
				
				// add triple stop_id;a;geo:SpacialThing
				m.add(
						id,
						RDF.type,
						m.createResource("http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing")
				);
				// add triple stop_id;a;stop_name
				m.add(
						id,
						RDFS.label,
						stop_name,
						XSDDatatype.XSDstring
				);
				m.add(
						id,
						RDFS.range,
						stop_lat,
						XSDDatatype.XSDdecimal
				);
				m.add(
						id,
						RDFS.range,
						stop_lon,
						XSDDatatype.XSDdecimal
				);
				
				
				// read next line
				line = reader.readLine();
			}
			reader.close();
			//m.write(System.out);
			//return m;
		} catch (IOException e) {
			e.printStackTrace();
		}
		m.write(System.out, "Turtle");
		return m;
	}

	static Model cours() {
		// create an empty Jena model
		Model m = ModelFactory.createDefaultModel();
		
		// define the prefix
		m.setNsPrefix("rdfs", RDFS.uri);
		m.setNsPrefix("rdf", RDF.uri);
		
		// create the node with URI "http://example.com/"
		Resource r = m.createResource("http://exemple.com/");
		
		// add triple
		m.add(
				r,
				RDF.type,
				m.createResource("http://ontology.org/Thing")
		);
		
		m.add(
				r,
				RDFS.label,
				"Something"
		);
		
		m.add(
				r,
				RDF.value,
				"232",
				XSDDatatype.XSDinteger
		);
		
		m.write(System.out, "Turtle");
		return m;
	}
	static void readFile() {
        String url = "https://territoire.emse.fr/kg/ontology.nt";
        Model model = ModelFactory.createDefaultModel();
        model.read(url);
        model.write(System.out);
    }
	public static void main(String[] args) {
		cours();
		TP();
		readFile();
		//
		//m.write(System.out);
		//System.out.println("--------------");
		//System.out.println(m);
		
		
		
	}
}
