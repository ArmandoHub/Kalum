package edu.kalum.enrollment.models.dao;


import edu.kalum.enrollment.models.domain.CarreraTecnica;

import java.util.List;

public interface ICarreraTecnicaDao {

    public List<CarreraTecnica> findAll();                      //<- consultar todos
    public CarreraTecnica findById(String carreraTecnicaId);    //<- consultar por ID
    public void save(CarreraTecnica carreraTecnica);            //<- INSERT
    public void update(CarreraTecnica carreraTecnica);          //<- UPDATE
    public  void delete(CarreraTecnica carreraTecnica);          //<- DELETE

}
