package com.frankmoley.lol.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.frankmoley.lol.data.entity.Service;
import com.frankmoley.lol.data.util.DatabaseUtils;

public class ServiceDao implements Dao<Service, UUID> {
    private static final Logger LOGGER = Logger.getLogger(ServiceDao.class.getName());
    
    private static final String GET_ALL = "SELECT service_id, name, price FROM services";
    private static final String GET_BY_ID = "SELECT service_id, name, price FROM services WHERE service_id = ?";
    
    @Override
    public List<Service> getAll() {
        List<Service> services = new java.util.ArrayList<>();
        
        try (Connection connection = DatabaseUtils.getConnection();
            // java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             java.sql.Statement statement = connection.createStatement();
             java.sql.ResultSet rs = statement.executeQuery(GET_ALL)) {
            services = this.processResultSet(rs);
        } catch (java.sql.SQLException e) {
            DatabaseUtils.handleSqlException("ServiceDao.getAll()", e, LOGGER);
            LOGGER.severe("Error retrieving services: " + e.getMessage());
        }
        return services;
    } 

    @Override
    public Service create(Service entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Service> getOne(UUID id) {
        try (PreparedStatement preparedStatement = DatabaseUtils.getConnection().prepareStatement(GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            java.sql.ResultSet rs = preparedStatement.executeQuery();
            List<Service> services = this.processResultSet(rs);
            if (!services.isEmpty()) {
                return Optional.of(services.get(0));
            }          
        } catch (java.sql.SQLException e) {
            DatabaseUtils.handleSqlException("ServiceDao.getOne()", e, LOGGER);
            LOGGER.severe("Error retrieving service with ID " + id + ": " + e.getMessage());
            
        } catch (Exception e) {
            LOGGER.severe("Unexpected error occurred: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Service update(Service entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private List<Service> processResultSet(java.sql.ResultSet rs) throws java.sql.SQLException {
        List<Service> services = new java.util.ArrayList<>();
        while (rs.next()) {
            Service service = new Service();
            // Set properties of service from ResultSet
            service.setServiceId((UUID) rs.getObject("service_id"));
            service.setName(rs.getString("name"));
            service.setPrice(rs.getBigDecimal("price"));
            services.add(service);
        }
        return services;
    }
}
