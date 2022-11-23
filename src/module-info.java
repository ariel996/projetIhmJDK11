/**
 * @author cdumas
 *
 */
module projetIhmJDK11 {
	
	requires javafx.controls;
	requires java.sql;
	requires postgresql;
	
	exports app to javafx.graphics;
}