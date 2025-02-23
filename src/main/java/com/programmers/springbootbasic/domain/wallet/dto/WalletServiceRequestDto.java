package com.programmers.springbootbasic.domain.wallet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class WalletServiceRequestDto {
    private final String email;
    private final UUID voucherId;
}
