package com.sprintburg.pos.repository;

import com.sprintburg.pos.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    /**
     * 1. Consulta por Rango de Fechas: Obtener todas las ventas realizadas
     * entre una fecha de inicio y una fecha de fin.
     * Crucial para el "Historial de Ventas" y la función de cierre de caja.
     */
    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 2. Consulta por Empleado y Rango de Fechas: Mostrar las ventas de un cajero específico.
     * Útil para reportes de rendimiento de empleados.
     */
    List<Sale> findByEmployeeIdAndSaleDateBetween(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 3. Consulta NATIVA o JPQL para Reportes: Calcular el total de ventas (suma de 'total')
     * en un rango de fechas.
     * Retorna el monto total recaudado.
     * * Usamos @Query para definir una consulta personalizada JPQL.
     */
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 4. Consulta NATIVA o JPQL para Reportes: Obtener el total de descuentos aplicados.
     * Importante para evaluar el impacto de las promociones.
     */
    @Query("SELECT SUM(s.discount) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    BigDecimal sumDiscountByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 5. Consulta por Método de Pago: Obtener todas las ventas pagadas en efectivo.
     * Útil para el cierre y conciliación de caja.
     */
    List<Sale> findByPaymentMethod(String paymentMethod);

    // Métodos heredados:
    // - save(Sale entity): Guardar una venta finalizada.
    // - findById(Long id): Buscar una venta específica (ej. para reimprimir un recibo).
}