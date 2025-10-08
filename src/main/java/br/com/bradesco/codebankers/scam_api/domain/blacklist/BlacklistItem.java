package br.com.bradesco.codebankers.scam_api.domain.blacklist;

import br.com.bradesco.codebankers.scam_api.domain.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity(name = "BlacklistItem")
@Table(name = "blacklist_items")
@EqualsAndHashCode(of = "id")
public class BlacklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @Column(name = "item_value")
    private String itemValue;

    private String source; //denuncia de cliente; lista extern xpto

    public BlacklistItem(){}

    public BlacklistItem(Long id, ItemType itemType, String itemValue, String source) {
        this.id = id;
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getItemValue() {
        return itemValue;
    }

    public String getSource() {
        return source;
    }
}
