package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.synthese.index;

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
public class Synthese extends Controller {
    
    
    public Result indexpost() {
        //Cas de redémarrage à 0
        int id_int=31;
        
        DynamicForm requestData = Form.form().bindFromRequest();
        
        String id = requestData.get("id");
        String util= requestData.get("util");
        String projet=requestData.get("projet");
        String comment= requestData.get("comment");
        String origin= requestData.get("origin");
        String text_form= requestData.get("text_form");
        
        //System.out.println("Param POST post_param:"+name);
        System.out.println("Param POST id:"+id);
        System.out.println("Param POST util:"+util);
        System.out.println("Param POST projet:"+projet);
        System.out.println("Param POST comment:"+comment);
        System.out.println("Param POST origin:"+origin);
        System.out.println("Param POST text_form:"+text_form);
        
      //------------------------------------
      //Début réinit
        String text_line="";
        String text_line_bis="";
        
        BufferedWriter bw = null;
		FileWriter fw = null;      
        try {
            //	System.out.println("Debug");
    		InputStream ips_content = new FileInputStream("conf/input.csv");
    		InputStreamReader ipsr_content = new InputStreamReader(ips_content);
	    	BufferedReader br_content = new BufferedReader(ipsr_content);
	    	fw = new FileWriter("conf/output.csv");
	    	bw = new BufferedWriter(fw);
            while ((text_line = br_content.readLine()) != null) {
                   System.out.println("Debug utilisateur courant:"+util);
                    if(!(text_line.split(";")[1].equals(util)))
                    {
                      //System.out.println("Debug 2 text_line:"+text_line);
                        text_line_bis=text_line+"\n";
                        bw.write(text_line_bis);
                    }
        	}//Fin while

            br_content.close();

    	} catch (Exception e) {

			System.out.println(e.toString());

	    } finally {

			try {

				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
	    	} catch (IOException ex) {
				ex.printStackTrace();
			}
		    
		}
		new File("conf/input.csv").delete();
		File f = new File("conf/output.csv");
		f.renameTo(new File("conf/input.csv"));
        
        
        //Ecriture du résultat    

        
		
		if(comment == null) comment="ok";
		if(text_form == null) text_form="ok";
		String vig="vig-";
		String pro="pro-";
		if(!(origin == null))
		{
		    if(origin.equals("vigilence")) comment=vig.concat(comment);
		    if(origin.equals("problem")) comment=pro.concat(comment);
		}
		if(!(origin == null))
		{
		    if(origin.equals("vigilence")) text_form=vig.concat(text_form);
		    if(origin.equals("problem")) text_form=pro.concat(text_form);
		}
        //Sauvegarde réponse précédente
       try {
			fw = new FileWriter("conf/results.csv",true);
			//bw = new BufferedWriter(fw);        
			
			fw.write("#"+util+";"+projet+";"+(id_int-1)+";"+text_form+"#\n");
			System.out.println("Ecrire dans le fichier de resultats");
			
       } catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
	    	} catch (IOException ex) {
				ex.printStackTrace();
			}
		    
		}
        
        
//---------------------------------------------------------
  //Fin réinit
  
        String identifiant="";
        projet="";
        text_line="";
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
        System.out.println("Récup. utilisateur avant render synthese:"+util);
        return ok(index.render(User.findByEmail(request().username()),identifiant_int, util,projet));
    }
    
    
}
