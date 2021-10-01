import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args){
        Connection con = null;
        try{
            Class.forName("org.sqlite.JDBC");

            String dbFile = "myfirst.db";
            con = DriverManager.getConnection("jdbc:sqlite:"+dbFile);

            String[] strs = {
                    "values ('Avicii', 'Male/Solo', '2000s 2010s', '2010', datetime('now','localtime'))",
                    "values ('The Chainsmokers', 'Male/Duet', '2010s 2020s', '2012', datetime('now','localtime'))",
                    "values ('Zedd', 'Male/Solo', '2000s 2010s 2020s', '2005', datetime('now','localtime'))",
                    "values ('Marshmello', 'Male/Solo', '2010s 2020s', '2015', datetime('now','localtime'))",
                    "values ('AKMU', 'Mix/Duet', '2010s 2020s', '2012', datetime('now','localtime'))",
                    "values ('방탄소년단','Male/Group','2010s 2020s','2013', datetime('now','localtime'))"
            };

            int cnt;

            //CREATE
            System.out.println("\n데이터 추가");
            Statement state2 = con.createStatement();
            String sql2 = "insert into g_artists (name, a_type, a_year, debut, regdate)";

            for(String str : strs) {
                cnt = state2.executeUpdate(sql2+str);
                if (cnt > 0)
                    System.out.println("새로운 데이터가 추가되었습니다.");
                else
                    System.out.println("데이터 오류");
            }
            state2.close();

            printDB(con);

            //UPDATE
            System.out.println("\n데이터 수정");
            Statement state3 = con.createStatement();
            String sql3 = "update g_artists set a_year = '2010s'"+
                    "where id = 1";
            cnt = state3.executeUpdate(sql3);

            if(cnt>0)
                System.out.println("데이터가 수정되었습니다.");
            else
                System.out.println("데이터 오류");
            state3.close();

            printDB(con);


            //DELETE
            System.out.println("\n데이터 삭제");
            Statement state4 = con.createStatement();
            String sql4 = "delete from g_artists "+
                    "where name = 'Zedd'";
            cnt = state4.executeUpdate(sql4);

            if(cnt>0)
                System.out.println("데이터가 삭제되었습니다.");
            else
                System.out.println("데이터 오류");
            state4.close();

            printDB(con);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(con!=null) {
                try {
                    con.close();
                }catch (Exception e){}
            }
        }
    }

    //READ
    public static void printDB (Connection con){
        try {
            System.out.println("\n데이터 목록");
            Statement state1 = con.createStatement();
            String sql1 = "Select * from g_artists";
            ResultSet rs1 = state1.executeQuery(sql1);

            while (rs1.next()) {
                System.out.println(rs1.getString("id") + " " + rs1.getString("name"));
            }
            state1.close();
        }catch (Exception e){}
    }
}