package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.Doctor;

/**
 * DAO associé aux médecins.
 * Doit assurer les fonctionnalités CRUD : Create, Retrieve, Update et Delete.
 *
 * @author Philippe TANGUY - révisé par Thierry Duval pour usage simplifié avec TP IHM
 */
public class DoctorDAO {

  public DoctorDAO () {

  }


  //-----------------------------------------------------------------------------
  // RETRIEVE
  //-----------------------------------------------------------------------------

   /**
    * Recherche d'un médecin par son ID.
    *
    * @param lastName
    * @param prenom
    * @return
    * @throws MedicalRecordException
    */
    public Doctor find (int doctorID) throws MedicalRecordException {
       try {
          Doctor doctor = null ;
          String sql = "select * from medecin where medecin_id=?" ;
          Connection conn = DBUtil.getConnection () ;
          PreparedStatement ps = conn.prepareStatement (sql) ;
          ps.setInt(1, doctorID) ;
          ResultSet rs = ps.executeQuery () ;
          while (rs.next ()) {
             doctor = new Doctor (rs.getInt("medecin_id"), rs.getString ("rpps"), rs.getString ("nom"), rs.getString ("prenom"),
                   rs.getString ("adresse"), rs.getString ("telephone"), rs.getString ("specialite")) ;
          }
          rs.close () ;
          ps.close () ;
          conn.close () ;
          return doctor ;
       } catch (SQLException sqle) {
          throw new MedicalRecordException (sqle.getMessage ()) ;
       }
    }


   public List<Doctor> findAll () throws MedicalRecordException {
      try {
         List<Doctor> doctors = new ArrayList<> () ;
         String sql = "select * from medecin order by prenom" ;
         Connection conn = DBUtil.getConnection () ;
         PreparedStatement ps = conn.prepareStatement (sql) ;
         ResultSet rs = ps.executeQuery () ;
         while (rs.next ()) {
            Doctor doctor = new Doctor (rs.getInt("medecin_id"), rs.getString ("rpps"), rs.getString ("nom"), rs.getString ("prenom"),
                  rs.getString ("adresse"), rs.getString ("telephone"), rs.getString ("specialite")) ;
            doctors.add (doctor) ;
         }
         rs.close () ;
         ps.close () ;
         conn.close () ;
         return doctors ;
      } catch (SQLException sqle) {
         throw new MedicalRecordException (sqle.getMessage ()) ;
      }
   }

}
