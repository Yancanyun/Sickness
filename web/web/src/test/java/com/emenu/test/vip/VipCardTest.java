package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.service.vip.VipCardService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * VipCardTest
 *
 * @author: yangch
 * @time: 2016/1/19 8:59
 */
public class VipCardTest extends AbstractTestCase {
    @Autowired
    private VipCardService vipCardService;

    @Test
    public void newVipCard() throws SSException {
        VipCard vipCard = new VipCard();
        vipCard.setVipPartyId(6);
        vipCard.setCardNumber("20160119001");
        vipCard.setPhysicalNumber("");
        vipCard.setPermanentlyEffective(0);
        vipCard.setOperatorPartyId(67);
        vipCardService.newVipCard(vipCard);
    }

    @Test
    public void listAll() throws SSException {
        List<VipCard> vipCardList = vipCardService.listAll();

        for (VipCard vipCard : vipCardList){
            System.out.println("id:" + vipCard.getId() + " vipPartyId:" + vipCard.getVipPartyId()
                    + " cardNumber:" + vipCard.getCardNumber() + " physicalNumber:" + vipCard.getPhysicalNumber()
                    + " validityTime:" + vipCard.getValidityTime() + " permanentlyEffective:" + vipCard.getPermanentlyEffective()
                    + " operatorPartyId:" + vipCard.getOperatorPartyId() + " status:" + vipCard.getStatus()
                    + " createdTime:" + vipCard.getCreatedTime());
        }
    }

    @Test
    public void listAllDto() throws SSException {
        List<VipCardDto> vipCardDtoList = vipCardService.listAllVipCardDto();

        for (VipCardDto vipCardDto : vipCardDtoList){
            System.out.println("id:" + vipCardDto.getVipCard().getId() + " vipName:" + vipCardDto.getVipInfo().getName()
                    + " vipPhone:" + vipCardDto.getVipInfo().getPhone()
                    + " cardNumber:" + vipCardDto.getVipCard().getCardNumber() + " physicalNumber:" + vipCardDto.getVipCard().getPhysicalNumber()
                    + " validityTime:" + vipCardDto.getVipCard().getValidityTime() + " permanentlyEffective:" + vipCardDto.getVipCard().getPermanentlyEffective()
                    + " operator:" + vipCardDto.getOperator() + " status:" + vipCardDto.getVipCard().getStatus()
                    + " createdTime:" + vipCardDto.getVipCard().getCreatedTime());
        }
    }

    @Test
    public void delById() throws SSException {
        vipCardService.delById(2);
    }
}