package com.frankmoley.lol;

import java.util.List;
import java.util.Optional;

import com.frankmoley.lol.data.dao.ServiceDao;
import com.frankmoley.lol.data.entity.Service;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ServiceDao serviceDao = new ServiceDao();
        List<Service> services = serviceDao.getAll();
        System.out.println("*** Services ***");
        System.out.println("\n*** GET ALL ***");
        for (Service service : services) {
            System.out.println(service);    
        }
        Optional<Service> serviceOpt = serviceDao.getOne(services.get(0).getServiceId());
        System.out.println("\n*** GET ONE ***\n");
        if (serviceOpt.isPresent()) {
            System.out.println(serviceOpt.get());
        } else {
            System.out.println("Service not found");
        }
    }
}
