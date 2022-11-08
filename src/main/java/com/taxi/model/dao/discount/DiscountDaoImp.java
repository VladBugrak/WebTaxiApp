package com.taxi.model.dao.discount;

import com.taxi.controller.exceptions.NonUniqueObjectException;
import com.taxi.controller.exceptions.ObjectNotFoundException;
import com.taxi.controller.exceptions.PastDateEditingException;
import com.taxi.model.dao.discount.DiscountDao;
import com.taxi.model.entity.Discount;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImp implements DiscountDao {

    private Connection connection;

    protected DiscountDaoImp(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Discount create(Discount discount) {

        final LocalDate EFFECTIVE_DATE = discount.getEffectiveDate();

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
                INSERT into discounts
                (
                effective_date,
                discount_level,
                total_sum_condition,
                discount_percentage
                ) VALUES (?,?,?,?) 
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp timestamp = Timestamp.valueOf(EFFECTIVE_DATE.atStartOfDay());
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setInt(2, discount.getDiscountLevel());
            preparedStatement.setDouble(3, discount.getTotalSumCondition());
            preparedStatement.setDouble(4, discount.getDiscountPercentage());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                discount.setId(resultSet.getInt(1));
                return discount;
            } else {
                return null;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Discount with effective date  \"")
                    .append(discount.getEffectiveDate())
                    .append("\" and level \"")
                    .append(discount.getDiscountLevel())
                    .append("\" already exist")
            ;

            throw new NonUniqueObjectException(stringBuffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private Discount extractDiscountFromResultSet(ResultSet resultSet) throws SQLException{

       Discount discount = new Discount();
       discount.setId(resultSet.getInt("id"));
       discount.setDiscountLevel(resultSet.getInt("discount_level"));
       discount.setEffectiveDate(resultSet.getTimestamp("effective_date").toLocalDateTime().toLocalDate());
       discount.setTotalSumCondition(resultSet.getDouble("total_sum_condition"));
       discount.setDiscountPercentage(resultSet.getDouble("discount_percentage"));

       return discount;

    }

    @Override
    public Discount findById(int id) {
        final String QUERY  = """
        select *
        from
        discounts
        where
        discounts.id = ?
        """;




        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Discount discount = extractDiscountFromResultSet(resultSet);
                return discount;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Discount> findAll() {

        final String QUERY  = """
        select *
        from
        discounts
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Discount> discountList = new ArrayList<>();
            Discount discount;
            while (resultSet.next()){
                discount = extractDiscountFromResultSet(resultSet);
                discountList.add(discount);
            }
            return discountList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Discount discount) {
        final LocalDate EFFECTIVE_DATE = discount.getEffectiveDate();

        if(findById(discount.getId()) == null){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("The discount object with id =")
                    .append(discount.getId())
                    .append(" does not exist in the data base. So you can't update it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        if(EFFECTIVE_DATE.isBefore(LocalDate.now()) || EFFECTIVE_DATE.isEqual(LocalDate.now())){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("You have tried to update a discount which effective date is ")
                    .append(EFFECTIVE_DATE)
                    .append(", which is equal or less then today's date ")
                    .append(LocalDate.now())
            ;
            throw new PastDateEditingException(stringBuffer.toString());
        }


        final String UPDATE_QUERY = """
                UPDATE discounts 
                SET 
                effective_date=?,
                discount_level =?,
                total_sum_condition=?, 
                discount_percentage=?  
                where id=?
                """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)){



            preparedStatement.setTimestamp(1, Timestamp.valueOf(discount.getEffectiveDate().atStartOfDay()));
            preparedStatement.setInt( 2, discount.getDiscountLevel());
            preparedStatement.setDouble( 3, discount.getTotalSumCondition());
            preparedStatement.setDouble( 4, discount.getDiscountPercentage());
            preparedStatement.setInt(5,discount.getId());

            return preparedStatement.executeUpdate()>0;
        } catch (SQLIntegrityConstraintViolationException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                    .append("Discount with level  \"")
                    .append(discount.getDiscountLevel())
                    .append("\" and effective date \"")
                    .append(discount.getEffectiveDate())
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
                    .append("The Discount object with id =")
                    .append(id)
                    .append(" does not exist in the data base. So you can't delete it");
            throw  new ObjectNotFoundException(stringBuffer.toString());
        }

        final String QUERY = """           
               DELETE from discounts 
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
    public List<Discount> getValidDiscountsOnDate(LocalDate localDate) {

        final String QUERY = """
                select
                d.id,
                d.discount_level,
                d.effective_date,
                d.total_sum_condition,
                d.discount_percentage
                from
                discounts as d
                inner join
                (select
                discount_level,
                max(effective_date) as effective_date
                from
                discounts
                where effective_date <= ?
                group by discount_level) as selection_condition
                on
                d.effective_date = selection_condition.effective_date
                and
                d.discount_level = selection_condition.discount_level                
                """;


        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Discount> discountList = new ArrayList<>();
            Discount discount;
            while (resultSet.next()){
                discount = extractDiscountFromResultSet(resultSet);
                discountList.add(discount);
            }
            return discountList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Discount getValidDiscountOnDateAndSum(LocalDate localDate, double sumOfCustomerOrders) {
       final String QUERY = """        
               select
               -- main request
               d.id,
               d.discount_level,
               d.effective_date,
               d.total_sum_condition,
               d.discount_percentage
               from
               discounts as d
               -- condition for selecting valid discounts for the date and amount
               inner join
               (select
               discount_level,
               max(effective_date) as effective_date
               from
               discounts
               where effective_date <= ? -- Parameter 1 localDate
               group by discount_level) as selection_condition
               on
               d.effective_date = selection_condition.effective_date
               and
               d.discount_level = selection_condition.discount_level
               and 
               d.total_sum_condition <= ? -- Parameter 2 - sumOfCustomerOrders
               order by
               d.discount_percentage desc,
               d.total_sum_condition desc,
               d.discount_level desc,
               d.effective_date desc
               limit 1                        
               """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {


            preparedStatement.setTimestamp(1,Timestamp.valueOf(localDate.atStartOfDay()));
            preparedStatement.setDouble(2,sumOfCustomerOrders);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Discount discount = extractDiscountFromResultSet(resultSet);
                return discount;
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
