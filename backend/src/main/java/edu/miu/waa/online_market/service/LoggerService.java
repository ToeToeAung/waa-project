package edu.miu.waa.online_market.service;
import edu.miu.waa.online_market.entity.Logger;
import edu.miu.waa.online_market.repo.LoggerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public interface LoggerService {

   public void logOperation(String operation);
}
