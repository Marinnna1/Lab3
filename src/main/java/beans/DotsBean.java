package beans;

import lombok.Data;
import model.Dot;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
@Data
public class DotsBean {
    private Dot newDot = new Dot();
    private String lastR = null;
    private String errorMessage;
    private String coordX;
    private String coordY;
    private String coordR;
    private String isFromGraphic="0";

    private List<Dot> dots = new ArrayList<Dot>();


    public void addDot() throws SQLException, ClassNotFoundException {
        newDot.setR(lastR);
        dots.add(newDot);
        save(Integer.parseInt(newDot.getX()), Double.parseDouble(newDot.getY()), Integer.parseInt(newDot.getR()), newDot.getResult());
        newDot = new Dot();
    }

    public void drawDot() throws SQLException, ClassNotFoundException {
        newDot.setXYR(coordX,coordY,coordR);
        checkResult(Integer.parseInt(coordX), Double.parseDouble(coordY), Integer.parseInt(coordR));
        dots.add(newDot);
        save(Integer.parseInt(coordX), Double.parseDouble(coordY), Integer.parseInt(coordR), newDot.getResult());
        newDot = new Dot();
    }

    public void checkData() throws IOException, InterruptedException {
//        if(isFromGraphic.equals("0")) {
        System.out.println("vrode 0 ? "+isFromGraphic);
        try {
            int x = Integer.parseInt(newDot.getX());
            double y = Double.parseDouble(newDot.getY().replace(',','.'));
            int r = Integer.parseInt(lastR);
            checkParameters();
        }
        catch (Exception e){
            errorMessage = "X, Y, R must be numbers!";
            goToErrorPage(errorMessage);
        }
//        }
//        else
//        {
//            System.out.println("vrode 1 ? "+isFromGraphic);
//            newDot.setX(coordX);
//            newDot.setY(coordY);
//            newDot.setR(coordR);
//            dots.add(newDot);
//            newDot = new Dot();
//        }
    }


    private void checkParameters() throws IOException {


        try {

            double y = Double.parseDouble(newDot.getY().replace(',', '.'));
            int x = Integer.parseInt(newDot.getX());
            int r = Integer.parseInt(lastR);

            if (!(y <= -5 || y >= 3)) {
                if (!(x < -3 || x > 3 || Double.parseDouble(newDot.getX()) % 1 != 0)) {
                    if (!(r < 1 || r > 5 || Double.parseDouble(lastR) % 1 != 0)) {
                        checkResult(x, y, r);
                        addDot();
                    } else {
                        errorMessage = "R must be integer in range [1;5]";
                        goToErrorPage(errorMessage);
                    }
                } else {
                    errorMessage = "X must be integer in range [-3;3]";
                    goToErrorPage(errorMessage);
                }
            } else {
                errorMessage = "Y must be in range (-5;3)";
                goToErrorPage(errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            goToErrorPage("SOMEerror");
        }

    }


    private void goToErrorPage(String errorMessage) throws IOException {
        newDot.setX(null);
        newDot.setY(null);
        lastR=null;
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("errorMessage", errorMessage);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/views/error.xhtml");
    }

    public void save(int x, double y, int r, String result) throws ClassNotFoundException, SQLException {
        String userName = "postgres";
        String password1 = "ilove";
        String connectionUrl = "jdbc:postgresql://localhost:5433/postgres";
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(connectionUrl, userName, password1);
        System.out.println("Connected");
        Statement statement = connection.createStatement();
        String query = "INSERT INTO results (x,y,r,result) values (" + x + ", " +
                y + ", " + r + ", '" +
                result + "')";
        statement.executeUpdate(query);
    }


    public void toggle(ActionEvent event) {
        UIComponent component = event.getComponent();
        String value = (String) component.getAttributes().get("value");
        lastR = value;
    }

    private void checkResult(double x, double y, double r) {
        if (x >= 0) {
            if (y >= 0) {
                if (y <= (r/2 -x/2)) {
                    newDot.setResult("Yes!");
                }
            } else {
                if (y >= (-r/2) && x <= r) {
                    newDot.setResult("Yes!");
                }
            }
        } else {
            if (y >= 0) {
                if ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r / 2, 2)) {
                    newDot.setResult("Yes!");
                }
            }
        }
        if (newDot.getResult() == null) {
            newDot.setResult("No!");
        }
    }
}


