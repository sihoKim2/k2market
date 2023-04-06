package com.DWmarket.market.Repository;

import com.DWmarket.market.constant.ItemSellStatus;
import com.DWmarket.market.dto.*;
import com.DWmarket.market.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ReportRepositoryUserImpl implements ReportRepositoryUser{
    private JPAQueryFactory queryFactory;

    public ReportRepositoryUserImpl(EntityManager en){
        this.queryFactory= new JPAQueryFactory(en);
    }
    private BooleanExpression itemNmLike(String searchQuery){ //querydls 을 사용하지 않을경우  itemRepository에 주석처리 되어있는거 쓴다
        return  StringUtils.isEmpty(searchQuery) ? null : QReport.report.title.like("%"+searchQuery+"%");
    }
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();
        if (StringUtils.equals("all" , searchDateType)|| searchDateType==null){
            return null;
        }else if (StringUtils.equals("1d" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1m" , searchDateType)){
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("6m" , searchDateType)){
            dateTime = dateTime.minusDays(6);
        }
        return QSalesBulletinBoard.salesBulletinBoard.regTime.after(dateTime);
    }
    public BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QSalesBulletinBoard.salesBulletinBoard.itemSellStatus.eq(searchSellStatus);
    }
    public BooleanExpression  searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)){ //상품명 이나 등록자에 있으면 조회
            return QSalesBulletinBoard.salesBulletinBoard.itemNm.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy", searchBy)){
            return QSalesBulletinBoard.salesBulletinBoard.createBy.like("%"+searchQuery+"%");
        }
        return null;
        //searchBy의 값에 따라 상품 또는 상품 등록자의 아이디에 검색어가 포함되어있는 상품을 조회
    }

    @Override
    public Page<Report> getAdminPage(ReportSearchDto reportSearchDto, Pageable pageable) {
        QueryResults<Report> results = queryFactory.selectFrom(QReport.report)
                .where(regDtsAfter(reportSearchDto.getSearchDataType()), //날짜
                        searchSellStatusEq(reportSearchDto.getSearchSellStatus()),
                        searchByLike(reportSearchDto.getSearchBy(),
                                reportSearchDto.getSearchQuery()))
                .orderBy(QReport.report.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Report> content = results.getResults();
        Long total = results.getTotal();
        //System.out.println(content.get(0).getItemSellStatus() + " - SalesBulletinBoardRepositoryUserImpl");
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<MainReportDto> getReportPage(ReportSearchDto reportSearchDto, Pageable pageable) {
        QReport item = QReport.report;
        QReportImg itemImg=QReportImg.reportImg;
        List<MainReportDto> content =queryFactory.select(new QMainReportDto(item.id, item.title, item.detail, itemImg.imgUrl))
                .from(itemImg)
                .join(itemImg.report, item)
                .where(itemImg.mainImgYn.eq("Y"))
                .where(itemNmLike(reportSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total=queryFactory
                .select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.report,item)
                .where(itemImg.mainImgYn.eq("Y"))
                .where(itemNmLike(reportSearchDto.getSearchQuery()))
                .fetchOne();
        return new PageImpl<>(content,pageable,total);
    }
}
