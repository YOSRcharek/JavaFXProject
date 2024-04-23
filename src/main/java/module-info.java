module crud {
	exports Repository;
	exports test;
	exports Database;
	exports Model;
	exports Application.Controllers;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	opens Application.Controllers;
}