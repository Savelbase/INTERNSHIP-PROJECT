package com.rmn.toolkit.cards.command.repository;

import com.rmn.toolkit.cards.command.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptsRepository extends JpaRepository<Receipt, String> {
}
