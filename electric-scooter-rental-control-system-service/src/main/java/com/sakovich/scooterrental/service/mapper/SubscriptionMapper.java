package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.ISubscriptionMapper;
import com.sakovich.scooterrental.dao.IUserDao;
import com.sakovich.scooterrental.model.Subscription;
import com.sakovich.scooterrental.model.dto.SubscriptionDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class SubscriptionMapper implements ISubscriptionMapper {

    private final ModelMapper mapper;
    private final IUserDao userDao;

    @Autowired
    public SubscriptionMapper(ModelMapper mapper, IUserDao userDao) {
        this.mapper = mapper;
        this.userDao = userDao;
    }

    @PostConstruct
    public void setupMapper() {
        TypeMap<Subscription, SubscriptionDto> typeMap = mapper.getTypeMap(Subscription.class, SubscriptionDto.class);
        if (typeMap == null) { // if not  already added
            mapper.createTypeMap(Subscription.class, SubscriptionDto.class)
                    .addMappings(m -> m.skip(SubscriptionDto::setUserId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(SubscriptionDto.class, Subscription.class)
                    .addMappings(m -> m.skip(Subscription::setUser)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(Subscription source, SubscriptionDto destination) {
        destination.setUserId(getUserId(source));
    }

    private Long getUserId(Subscription source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUser().getId();
    }

    void mapSpecificFields(SubscriptionDto source, Subscription destination) {
        destination.setUser(userDao.findById(source.getUserId()).orElse(null));
    }

    Converter<Subscription, SubscriptionDto> toDtoConverter() {
        return context -> {
            Subscription source = context.getSource();
            SubscriptionDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<SubscriptionDto, Subscription> toEntityConverter() {
        return context -> {
            SubscriptionDto source = context.getSource();
            Subscription destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    @Override
    public Subscription toEntity(SubscriptionDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Subscription.class);
    }

    @Override
    public SubscriptionDto toDto(Subscription entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, SubscriptionDto.class);
    }
}
