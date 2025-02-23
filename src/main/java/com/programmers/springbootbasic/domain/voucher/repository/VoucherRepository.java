package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID voucherId);

    void update(Voucher voucher);

    void delete(Voucher voucher);

    List<Voucher> findByVoucherType(int voucherType);

    List<Voucher> findByDate(LocalDate date);

    List<Voucher> findAll();

    void deleteAll();
}
