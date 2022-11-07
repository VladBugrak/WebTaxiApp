package com.taxi.model.dao;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.controller.exceptions.PastDateEditingException;
import com.taxi.model.entity.CarCategory;
import com.taxi.model.entity.Tariff;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TariffDaoImp implements TariffDao{

    private Connection connection;

    TariffDaoImp(Connection connection) {

        this.connection = connection;
    }

    private final String SEARCHING_QUERY = """
            select
            -- car category fields:
            cc.id as cc_id,
            cc.name as cc_name,
            cc.name_ua as cc_name_ua,
            -- tariff fields:
            t.id,
            t.effective_date,
            t.car_category_id,
            t.fare_for_call,
            t.fare_per_km
            --
            from tariffs as t
            inner join car_category as cc
            on t.car_category_id = cc.id
            """;




    private boolean isCarCategoryExists(CarCategory carCategory){
        final String QUERY = """
                SELECT * from car_category where id=?;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setInt(1, carCategory.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public Tariff create(Tariff tariff) {

       if(!isCarCategoryExists((tariff.getCarCategory()))){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The CarCategory object with id =")
                    .append(tariff.getCarCategory().getId())
                    .append(" does not exist in the data base.");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        final LocalDate EFFECTIVE_DATE = tariff.getEffectiveDate();

        if(EFFECTIVE_DATE.isBefore(LocalDate.now())){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("You have tried to create a tariff which effective date ")
                    .append(EFFECTIVE_DATE)
                    .append(" which is less then today's date ")
                    .append(LocalDate.now())
                    ;
            throw new PastDateEditingException(stringBuffer.toString());
        }

        final String QUERY = """
                INSERT into tariffs
                (
                effective_date,
                car_category_id,
                fare_for_call,
                fare_per_km
                ) VALUES (?,?,?,?) 
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp timestamp = Timestamp.valueOf(EFFECTIVE_DATE.atStartOfDay());
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setInt(2, tariff.getCarCategory().getId());
            preparedStatement.setDouble(3, tariff.getFareForCall());
            preparedStatement.setDouble(4, tariff.getFarePerKm());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                tariff.setId(resultSet.getInt(1));
                return tariff;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Tariff with car category  \"")
                    .append(tariff.getCarCategory())
                    .append("\" and effective time \"")
                    .append(tariff.getEffectiveDate())
                    .append("\" already exist")
            ;

            throw new NonUniqueObjectException(stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CarCategory extractCarCategoryFromResultSet(ResultSet resultSet) throws SQLException{

        CarCategory carCategory = new CarCategory();
        carCategory.setId(resultSet.getInt("cc_id"));
        carCategory.setName(resultSet.getString("cc_name"));
        carCategory.setNameUA(resultSet.getString("cc_name_ua"));
        return carCategory;

    }

    private Tariff extractTariffFromResultSet(ResultSet resultSet) throws SQLException{
        Tariff tariff = new Tariff();
        tariff.setId(resultSet.getInt("id"));
        tariff.setEffectiveDate(resultSet.getTimestamp("effective_date").toLocalDateTime().toLocalDate());
        tariff.setCarCategory(extractCarCategoryFromResultSet(resultSet));
        tariff.setFareForCall(resultSet.getDouble("fare_for_call"));
        tariff.setFarePerKm(resultSet.getDouble("fare_per_km"));

        return tariff;
    }

    @Override
    public Tariff findById(int id) {
        final String QUERY  = SEARCHING_QUERY + "where t.id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
               Tariff tariff = extractTariffFromResultSet(resultSet);
               return tariff;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tariff> findAll() {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCHING_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Tariff> tariffList = new ArrayList<>();
            Tariff tariff;
            while (resultSet.next()){
                tariff = extractTariffFromResultSet(resultSet);
                tariffList.add(tariff);
            }
            return tariffList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Tariff tariff) {

        final LocalDate EFFECTIVE_DATE = tariff.getEffectiveDate();

        if(findById(tariff.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The tariff object with id =")
                    .append(tariff.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        if(EFFECTIVE_DATE.isBefore(LocalDate.now()) || EFFECTIVE_DATE.isEqual(LocalDate.now())){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("You have tried to update a tariff which effective date is")
                    .append(EFFECTIVE_DATE)
                    .append(", which is equal or less then today's date ")
                    .append(LocalDate.now())
            ;
            throw new PastDateEditingException(stringBuffer.toString());
        }


        final String UPDATE_QUERY = """
                UPDATE tariffs 
                SET 
                effective_date=?,
                car_category_id=?, 
                fare_for_call=?, 
                fare_per_km=?          
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)){

            System.out.println(connection);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(tariff.getEffectiveDate().atStartOfDay()));
            preparedStatement.setInt( 2, tariff.getCarCategory().getId());
            preparedStatement.setDouble( 3, tariff.getFareForCall());
            preparedStatement.setDouble( 4, tariff.getFarePerKm());
            preparedStatement.setInt(5,tariff.getId());

            return preparedStatement.executeUpdate()>0;
        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Tariff with car category  \"")
                    .append(tariff.getCarCategory())
                    .append("\" and effective time \"")
                    .append(tariff.getEffectiveDate())
                    .append("\" already exist")
            ;
            throw new NonUniqueObjectException(stringBuffer.toString());
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
                    .append("The Tariff object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        final String QUERY = """           
               DELETE from tariffs 
               where id=?;
                """;



        try(PreparedStatement preparedStatement = connection.prepareStatement(QUERY)){

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public Tariff getTariffOnDate(LocalDate localDate, CarCategory carCategory) {
        final String SELECTION_CONDITION_QUERY = """
                
                -- selection condition
                inner join
                   (select
                   car_category_id,
                   max(effective_date) as effective_date
                   from tariffs
                   where
                   effective_date <= ?
                   and car_category_id = ?
                   group by car_category_id) selection_condition
                   on
                   t.effective_date = selection_condition.effective_date
                   and
                   t.car_category_id = selection_condition.car_category_id
                """;
        final String QUERY = SEARCHING_QUERY + SELECTION_CONDITION_QUERY;
//        System.out.println(QUERY);


        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            preparedStatement.setInt(2,carCategory.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Tariff tariff = extractTariffFromResultSet(resultSet);
                return tariff;
            } else {
                return null;
            }

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
