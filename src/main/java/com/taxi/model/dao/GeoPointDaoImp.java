package com.taxi.model.dao;

import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.controller.exceptions.NonUniqGeoPointException;
import com.taxi.model.entity.GeoPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeoPointDaoImp implements GeoPointDao {

    private Connection connection;

    GeoPointDaoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public GeoPoint create(GeoPoint geoPoint) {
        String query = """                    
                            INSERT into geolocation_point 
                            ( 
                            name, 
                            latitude, 
                            longitude                     
                            ) VALUES(?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {


            preparedStatement.setString(1, geoPoint.getName());
            preparedStatement.setDouble(2,geoPoint.getLatitude());
            preparedStatement.setDouble(3,geoPoint.getLongitude());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                geoPoint.setId(resultSet.getInt(1));

                return geoPoint;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Geolocation point with latitude =")
                    .append(geoPoint.getLatitude())
                    .append(" and longitude =")
                    .append(geoPoint.getLongitude())
                    .append(" already exist");

            throw new NonUniqGeoPointException( stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private GeoPoint extractGeoPointFromResultSet(ResultSet resultSet) throws SQLException {

       GeoPoint geoPoint = new GeoPoint();
       geoPoint.setId(resultSet.getInt("id"));
       geoPoint.setName(resultSet.getString("name"));
       geoPoint.setLatitude(resultSet.getDouble("latitude"));
       geoPoint.setLongitude(resultSet.getDouble("longitude"));

       return geoPoint;
    }

    @Override
    public GeoPoint findById(int id) {
        String query = """
                SELECT * from geolocation_point where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                GeoPoint geoPoint = extractGeoPointFromResultSet(resultSet);
                return geoPoint;
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GeoPoint> findAll() {
        String query = """
                SELECT * from geolocation_point;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<GeoPoint> geoPointList = new ArrayList<>();
            GeoPoint geoPoint;
            while (resultSet.next()){
                geoPoint = extractGeoPointFromResultSet(resultSet);
                geoPointList.add(geoPoint);
            }
            return geoPointList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(GeoPoint geoPoint) {

        if(findById(geoPoint.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The GeoPoint object with id =")
                    .append(geoPoint.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        String query = """
                UPDATE geolocation_point 
                SET 
                name=?,
                latitude=?, 
                longitude=?       
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){




            preparedStatement.setString(1,geoPoint.getName());
            preparedStatement.setDouble(2,geoPoint.getLatitude());
            preparedStatement.setDouble(3,geoPoint.getLongitude());
            preparedStatement.setDouble(4,geoPoint.getId());

            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {

        if(findById(id) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The GeoPoint object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }


        String query = """           
               DELETE from geolocation_point 
               where id=?;
                """;



        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
