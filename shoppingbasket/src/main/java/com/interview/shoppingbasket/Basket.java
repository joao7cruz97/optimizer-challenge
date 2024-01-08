package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = new BasketItem();
        basketItem.setProductCode(productCode);
        basketItem.setProductName(productName);
        basketItem.setQuantity(quantity);

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        // Exercise - implement this function
        Map<String, BasketItem> basketItemMap = new HashMap<>();

        // Iterar sobre os itens originais
        for (BasketItem item : items) {
            String productCode = item.getProductCode();

            // Verificar se já existe um BasketItem com o mesmo productCode no map
            if (basketItemMap.containsKey(productCode)) {
                // Se existir, adicionar a quantidade do item atual ao BasketItem existente
                BasketItem existingItem = basketItemMap.get(productCode);
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                // Se não existir, adicionar o BasketItem ao map
                basketItemMap.put(productCode, item);
            }
        }

        // Atualizar a lista original com os itens consolidados
        items = new ArrayList<>(basketItemMap.values());
    }
}
