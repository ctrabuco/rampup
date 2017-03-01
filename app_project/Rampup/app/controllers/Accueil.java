package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.accueil.index;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.io.*;

import play.data.DynamicForm;
import play.data.*;
import play.data.Form;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



/**
 * User: yesnault
 * Date: 22/01/12
 */
@Security.Authenticated(Secured.class)
public class Accueil extends Controller {
    
    public Result index() {
        
        
        System.out.println("Récup. utilisateur:"+Application.utilisateur);
        
        String identifiant="";
        String projet="";
        String text_line="";
        int identifiant_int=1;//Comportement par défaut, on repart de la première image
        int numero_ligne_content=0;
        
        try {
        	System.out.println("Debug");
    		InputStream ips_content = new FileInputStream("conf/input.csv");
    		InputStreamReader ipsr_content = new InputStreamReader(ips_content);
    		BufferedReader br_content = new BufferedReader(ipsr_content);
    
    		while ((text_line = br_content.readLine()) != null) {
    
    			numero_ligne_content++;
    
                System.out.println("Dans while,text_line.split(;)[1] : "+text_line.split(";")[1]);
                if(text_line.split(";")[0].equals("identifiant") && text_line.split(";")[1].equals(Application.utilisateur))
    			{
    			    System.out.println("Dans if");
    				identifiant=text_line.split(";")[3];
    				projet=text_line.split(";")[2];
    			}
    			
    		}//Fin while
    		br_content.close();
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	}
        if(!identifiant.equals(""))
        {
            identifiant_int=Integer.valueOf(identifiant);
        }
        System.out.println("identifiant_int:"+identifiant_int);
        
       // return ok(index.render(User.findByEmail(request().username()),identifiant_param));
        System.out.println("Récup. utilisateur avant render:"+Application.utilisateur);
        return ok(index.render(User.findByEmail(request().username()),identifiant_int, Application.utilisateur,projet));
    }
    
    
    public Result indexpost() {
        //Cas de redémarrage à 0
        int id_int=31;
        
        DynamicForm requestData = Form.form().bindFromRequest();

        String util= requestData.get("util");
        System.out.println("Récup. utilisateur:"+util);
        
        String identifiant="";
        String projet="";
        String text_line="";
        int identifiant_int=1;//Comportement par défaut, on repart de la première image
        int numero_ligne_content=0;
        
        try {
        	System.out.println("Debug");
    		InputStream ips_content = new FileInputStream("conf/input.csv");
    		InputStreamReader ipsr_content = new InputStreamReader(ips_content);
    		BufferedReader br_content = new BufferedReader(ipsr_content);
    
    		while ((text_line = br_content.readLine()) != null) {
    
    			numero_ligne_content++;
    
                System.out.println("Dans while,text_line.split(;)[1] : "+text_line.split(";")[1]);
                if(text_line.split(";")[0].equals("identifiant") && text_line.split(";")[1].equals(util))
    			{
    			    System.out.println("Dans if");
    				identifiant=text_line.split(";")[3];
    				projet=text_line.split(";")[2];
    			}
    			
    		}//Fin while
    		br_content.close();
    	} catch (Exception e) {
    		System.out.println(e.toString());
    	}
        if(!identifiant.equals(""))
        {
            identifiant_int=Integer.valueOf(identifiant);
        }
        System.out.println("identifiant_int:"+identifiant_int);
        
       // return ok(index.render(User.findByEmail(request().username()),identifiant_param));
        System.out.println("Récup. utilisateur avant render:"+Application.utilisateur);
        return ok(index.render(User.findByEmail(request().username()),identifiant_int, util,projet));
        
    }
    
    
}
