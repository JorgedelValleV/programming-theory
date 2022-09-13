package simulator.launcher;

/*
 * Examples of command-line parameters:
 * 
 *  -h
 *  -i resources/examples/ex4.4body.txt -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl ftcg
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl nlug
 *	-m gui -i resources/examples/ex4.4body.txt -o resources/output/salida.txt -s 10000 -dt 10000 -gl nlug
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.FallingToCenterGravityBuilder;
import simulator.factories.MassLosingBodyBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoGravityBuilder;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static Integer _stepsDefault=150;
	private final static String _modeDefault="batch";


	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static JSONObject _gravityLawsInfo = null;
	private static String _outFile=null;
	private static int steps;
	private static String _mode=null;
	

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<GravityLaws> _gravityLawsFactory;

	private static void init() {
		// initialize the bodies factory
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new BasicBodyBuilder());
		bodyBuilders.add(new MassLosingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);


		// initialize the gravity laws factory
		ArrayList<Builder<GravityLaws>> lawsBuilders = new ArrayList<>();
		lawsBuilders.add(new NewtonUniversalGravitationBuilder());
		lawsBuilders.add(new FallingToCenterGravityBuilder());
		lawsBuilders.add(new NoGravityBuilder());
		_gravityLawsFactory = new BuilderBasedFactory<GravityLaws>(lawsBuilders);
		
		
		
	
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseDeltaTimeOption(line);
			parseGravityLawsOption(line);
			parseOutputOption(line);
			parseStepsOption(line);
			
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());
		// mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution Mode. Possible values: ’batch’ (Batch mode), ’gui’ (Graphical User Interface mode). Default value: ’batch’.").build());
		//Output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file,where output is written. default value: the standard output").build());
		
		//steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("An integer representing the number of simulation steps. Default value: " + _stepsDefault + ".").build());
		
		// gravity laws -- there is a workaround to make it work even when
		// _gravityLawsFactory is null. 
		//
		String gravityLawsValues = "N/A";
		String defaultGravityLawsValue = "N/A";
		if (_gravityLawsFactory != null) {
			gravityLawsValues = "";
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gravityLawsValues.length() > 0) {
					gravityLawsValues = gravityLawsValues + ", ";
				}
				gravityLawsValues = gravityLawsValues + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}
			defaultGravityLawsValue = _gravityLawsFactory.getInfo().get(0).getString("type");
		}
		cmdLineOptions.addOption(Option.builder("gl").longOpt("gravity-laws").hasArg()
				.desc("Gravity laws to be used in the simulator. Possible values: " + gravityLawsValues
						+ ". Default value: '" + defaultGravityLawsValue + "'.")
				.build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode.equals("batch")) {
			throw new ParseException("An input file of bodies is required");
		}
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}
	private static void parseOutputOption(CommandLine line) throws ParseException{
		_outFile=line.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine line)throws ParseException{
		String step=line.getOptionValue("s", _stepsDefault.toString());
		try {
			steps = Integer.parseInt(step);
			assert (steps > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid steps value: " + step);
		}
	}
	private static void parseModeOption(CommandLine line)throws ParseException{
		_mode=line.getOptionValue("m",_modeDefault);
	}
	private static void parseGravityLawsOption(CommandLine line) throws ParseException {

		// this line is just a work around to make it work even when _gravityLawsFactory
		// is null, you can remove it when've defined _gravityLawsFactory
		if (_gravityLawsFactory == null)
			return;

		String gl = line.getOptionValue("gl");
		if (gl != null) {
			//++++++++++++++++++++++++++++++++
			//falta por hacer getInfo()FORMA LA LEY A PARTIR DEL STRING QUE MANDA EL USUARIO
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gl.equals(fe.getString("type"))) {
					_gravityLawsInfo = fe;
					break;
				}
			}
			if (_gravityLawsInfo == null) {
				throw new ParseException("Invalid gravity laws: " + gl);
			}
		} else {
			_gravityLawsInfo = _gravityLawsFactory.getInfo().get(0);
		}
		//A partir de aqui manejamos el JSON  de la ley
	}

	
	//++++++++++++++++++++++++++++
	//Una de las cosas que se hace es crear el simulador.
	//Al constructor le tengo que pasar la ley(llamar al createInstance con la ley)
	private static void startBatchMode() throws Exception {
		// create and connect components, then start the simulator
		
		InputStream is = new  FileInputStream(new File(_inFile));
		OutputStream os = _outFile == null ? System.out:new FileOutputStream(new File(_outFile));
		
//		Completa la implementaciÃ³n del mÃ©todo startBatchMode() de forma que cree una instancia del simulador y
//		del controlador; establezca las leyes de gravedad del simulador de acuerdo con lo especiï¬�cado a travÃ©s 
//		de la opcion -gl; cree los ï¬�cheros correspondientes de entrada y salida teniendo en cuenta las opciones
//		-i y -o, aÃ±ada los cuerpos al simulador (invocando al mÃ©todo loadBodies del controlador); e inicie la 
//		simulaciÃ³n llamando al mÃ©todo run del controlador.
		
		
		try {
			GravityLaws law=_gravityLawsFactory.createInstance(_gravityLawsInfo);
			PhysicsSimulator simulator=new PhysicsSimulator(law, _dtime);
			Controller c=new Controller(simulator, _bodyFactory,_gravityLawsFactory);
			c.loadBodies(is);
			c.run(steps, os);
		}catch(IllegalArgumentException e) {
			throw new Exception (e.getMessage());
		}
		
		
	}
	private static void startGUIMode() throws Exception {
		//falta controlar el que no sea argumento la entrada
		try {
			GravityLaws law=_gravityLawsFactory.createInstance(_gravityLawsInfo);
			PhysicsSimulator simulator=new PhysicsSimulator(law, _dtime);
			Controller ctrl=new Controller(simulator, _bodyFactory,_gravityLawsFactory);
			 

			if(_inFile!=null){
				InputStream is = new  FileInputStream(new File(_inFile));
				ctrl.loadBodies(is);
			}
			
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new MainWindow(ctrl);
				} 
			});
			
			
		}catch(IllegalArgumentException e) {
			throw new Exception (e.getMessage());
		}
		
	}
	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if(_mode.equals("batch")) {
			startBatchMode();
		}
		if(_mode.equals("gui")) {
			startGUIMode();
		}

	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
