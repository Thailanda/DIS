package de.dis2015;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.hibernate.Session;

/**
 * Created by Joanna on 25.06.2015.
 */
public class MainApp {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DataWarehouseModule());

        Session session = injector.getInstance(Session.class);
//        session.doWork(connection -> {
//            // Empty all articles
//            connection.prepareStatement("DELETE FROM VSISP15.ARTICLE").execute();
//
//            // Load from external DB
//            PreparedStatement statement = connection.prepareStatement("SELECT\n" +
//                    "  a.ArticleID as articleId,\n" +
//                    "  g.ProductGroupID as groupId,\n" +
//                    "  f.ProductFamilyID as familyId,\n" +
//                    "  cat.ProductCategoryID as categoryId,\n" +
//                    "  cat.name AS category,\n" +
//                    "  f.name AS family,\n" +
//                    "  g.name AS group,\n" +
//                    "  a.name AS article,\n" +
//                    "  a.preis AS price\n" +
//                    "FROM DB2INST1.ArticleID AS a\n" +
//                    "INNER JOIN DB2INST1.ProductGroupID AS g ON a.ProductGroupID = g.ProductGroupID\n" +
//                    "INNER JOIN DB2INST1.ProductFamilyID AS f ON g.ProductFamilyID = f.ProductFamilyID\n" +
//                    "INNER JOIN DB2INST1.ProductCategoryID AS cat ON f.ProductCategoryID = cat.ProductCategoryID\n");
//            ResultSet result = statement.executeQuery();
//
//            Transaction tx = session.beginTransaction();
//            while (result.next()) {
//                Article article = new Article();
//                article.setArticleId(result.getInt("articleid"));
//                article.setGroupId(result.getInt("groupId"));
//                article.setFamilyId(result.getInt("familyId"));
//                article.setCategoryId(result.getInt("categoryId"));
//                article.setCategory(result.getString("category"));
//                article.setFamily(result.getString("family"));
//                article.setGroup(result.getString("group"));
//                article.setArticle(result.getString("article"));
//                article.setPrice(result.getFloat("price"));
//
//                session.save(article);
//            }
//
//            session.flush();
//            tx.commit();
//
//            // Empty all shops
//            connection.prepareStatement("DELETE FROM VSISP15.SHOP").execute();
//
//            // Load shops from external DB
//            statement = connection.prepareStatement("SELECT \n" +
//                    "  s.ShopId as shopId,\n" +
//                    "  c.StadtId as cityId,\n" +
//                    "  r.REGIONID as regionId,\n" +
//                    "  l.LANDID as countryId,\n" +
//                    "  l.name as country,\n" +
//                    "  r.name as region,\n" +
//                    "  c.name as city,\n" +
//                    "  s.name as shopName\n" +
//                    "FROM DB2INST1.ShopID AS s\n" +
//                    "INNER JOIN DB2INST1.StadtID AS c ON c.StadtID = s.StadtID\n" +
//                    "INNER JOIN DB2INST1.RegionID AS r ON c.RegionID = r.RegionID\n" +
//                    "INNER JOIN DB2INST1.LandID AS l ON l.LandID = r.LandID");
//            result = statement.executeQuery();
//
//            tx = session.beginTransaction();
//            while (result.next()) {
//                Shop shop = new Shop();
//                shop.setShopId(result.getInt("shopid"));
//                shop.setCityId(result.getInt("cityid"));
//                shop.setRegionId(result.getInt("regionid"));
//                shop.setCountryId(result.getInt("countryid"));
//                shop.setCountryName(result.getString("country"));
//                shop.setRegionName(result.getString("region"));
//                shop.setCityName(result.getString("city"));
//                shop.setShopName(result.getString("shopName"));
//
//                session.save(shop);
//            }
//
//            session.flush();
//            tx.commit();
//
////            Just comment put if necessary!!!
//
//            // Empty all sales
//            connection.prepareStatement("DELETE FROM VSISP15.SALE").execute();
//
//            tx = session.beginTransaction();
//
//            Pattern datePattern = Pattern.compile("^(\\d{2})\\.(\\d{2})\\.(\\d{4})$");
//
//            // Open CSV
//            try {
//                File csvData = new File(MainApp.class.getResource("sales.csv").toURI());
//                CSVParser parser = CSVParser.parse(csvData, Charset.forName("ISO-8859-1"), CSVFormat.DEFAULT.withHeader().withDelimiter(';'));
//                int i = 0;
//                for (CSVRecord record: parser) {
//                    Sale sale = new Sale();
//                    sale.setShopName(record.get("Shop"));
//                    sale.setArticle(record.get("Artikel"));
//                    sale.setAmount(Integer.parseInt(record.get("Verkauft")));
//                    sale.setTurnover(Float.parseFloat(record.get("Umsatz").replace(',', '.')));
//
//                    // Split date into y, m, d
//                    String date = record.get("Datum");
//                    Matcher matcher = datePattern.matcher(date);
//
//                    if (matcher.matches()) {
//                        sale.setDay(Integer.parseInt(matcher.group(1)));
//                        sale.setMonth(Integer.parseInt(matcher.group(2)));
//                        sale.setYear(Integer.parseInt(matcher.group(3)));
//                    }
//
//                    session.save(sale);
//
//                    if (++i % 100 == 0) {
//                        session.flush();
//                        tx.commit();
//
//                        tx = session.beginTransaction();
//                    }
//
//                    if (i >= 500) {
//                        break;
//                    }
//                }
//            } catch (URISyntaxException | IOException e) {
//                e.printStackTrace();
//            }
//
//            session.flush();
//            tx.commit();
//
//            // Insert everything into star schema
//            tx = session.beginTransaction();
//
//            connection.prepareStatement("INSERT INTO STAR (SHOPID, SALESID, REGIONID, GROUPID, FAMILYID, COUNTRYID, " +
//                    "CITYID, CATEGORYID, ARTICLEID, AMOUNT, ARTICLE, CATEGORY, CITYNAME, COUNTRYNAME, DAY, FAMILY, " +
//                    "GROUP, MONTH, PRICE, REGIONNAME, SHOPNAME, TURNOVER, YEAR)\n" +
//                    "SELECT\n" +
//                    "  SHOPID, SALESID, REGIONID, GROUPID, FAMILYID, COUNTRYID, CITYID, CATEGORYID, ARTICLEID, " +
//                    "AMOUNT, SALE.ARTICLE, CATEGORY, CITYNAME, COUNTRYNAME, DAY, FAMILY, GROUP, MONTH, PRICE, " +
//                    "REGIONNAME, SALE.SHOPNAME, TURNOVER, YEAR\n" +
//                    "FROM SALE\n" +
//                    "INNER JOIN SHOP AS S ON S.SHOPNAME = SALE.SHOPNAME\n" +
//                    "INNER JOIN ARTICLE AS A ON A.ARTICLE = SALE.ARTICLE").execute();
//
//            tx.commit();
//        });

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setBaseResource(Resource.newClassPathResource("/web"));

        ContextHandler context = new ContextHandler();
        context.setContextPath("/data");
        context.setResourceBase(".");
        context.setAllowNullPathInfo(true);
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        context.setHandler(new AbstractHandler() {

            @Override
            public void handle(String s, Request baseRequest, HttpServletRequest request, HttpServletResponse
                    response) throws IOException, ServletException {

                Object data = new BasicDBObject("hello", "world");
                Session session = injector.getInstance(Session.class);
                
//                session.byId(STARTED.clone())

                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);
                response.getWriter().print(JSON.serialize(data));
            }
        });

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resourceHandler, context });

        Server server = new Server(1337);
        server.setHandler(handlers);

        // Start server
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
