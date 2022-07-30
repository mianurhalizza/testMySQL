package belajarsql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TestConnection {
	
	public static void main(String[] args) {
	// TODO Auto-generated method stub

	Connection conn = null;
	
	// Create siswa
			int id = insertSiswa(0, "Wahab", "laki-laki", 1, "08123");
			System.out.println(String.format("siswa telah ditambahkan", id));

			// Read or Select
			String sqlSelect = "SELECT * FROM siswa";		
			try {
				conn = MySQLJDBCUtil.getConnection();
				
				Statement stmt = (Statement) conn.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				
				while (rs.next()) {
					System.out.println(rs.getString("nama_siswa") + " dari==>" + rs.getString("no_telp"));
				}
				
			} catch(SQLException e) {
			}
			
			//Update
			update();
			
			//Delete
			delete(1); 
			
	}
	public static int insertSiswa(int id_siswa, String nama_siswa, String jenis_kelamin,int id_guru,String no_telp) {
		// insert
		ResultSet rs = null;
		int siswaId = 0;

		String sqlCreated = "INSERT INTO siswa(id_siswa,nama_siswa,jenis_kelamin,id_guru, no_telp)"
				+ "VALUES (?,?,?,?,?)"; // Create or Insert;

		try (Connection conn = MySQLJDBCUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sqlCreated, Statement.RETURN_GENERATED_KEYS);) {

			// set parameters for statement
			pstmt.setInt(1, id_siswa);
			pstmt.setString(2, nama_siswa);
			pstmt.setString(3, jenis_kelamin);
			pstmt.setInt(4, id_guru);
			pstmt.setString(5, no_telp);

			int rowAffected = pstmt.executeUpdate();
			if (rowAffected == 1) {

				// get candidate id
				rs = pstmt.getGeneratedKeys();
				if (rs.next())
					siswaId = rs.getInt(1);

			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return siswaId;
	}
	public static void update() {

        String sqlUpdate = "UPDATE siswa "
                + "SET nama_siswa = ? "
                + "WHERE id_siswa = ?";
        
        try (Connection conn = MySQLJDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

            // prepare data for update
            String nama_siswa = "Gilang";
            int id_siswa = 2;
            pstmt.setString(1, nama_siswa);
            pstmt.setInt(2, id_siswa);

            int rowAffected = pstmt.executeUpdate();
            
            // reuse the prepared statement
            nama_siswa = "Gulto,";
            id_siswa = 6;
            pstmt.setString(1, nama_siswa);
            pstmt.setInt(2, id_siswa);

            rowAffected = pstmt.executeUpdate();
            System.out.println(String.format("update %d", rowAffected));

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
	/**
	 * Delete or remove mahasiswa method
	 */
    public static void delete(int id) {

        String sqlDelete = "DELETE FROM siswa "
        				+"WHERE id_siswa = ?";
        
        try (Connection conn = MySQLJDBCUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {

            // prepare data for delete
            int id_siswa = 7;
            pstmt.setInt(1, id_siswa);

            int row = pstmt.executeUpdate();
            System.out.println(String.format("delete %d", row));


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
