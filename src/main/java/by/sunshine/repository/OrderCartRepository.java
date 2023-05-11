package by.sunshine.repository;

import by.sunshine.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
///почему когда я тут написал класс о мне надо было заимплементить  метод а после того как написал интерфейс то не надо
@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {

}
