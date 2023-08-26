package com.maksise4ka.microcats.contracts.cats.requests;

import java.util.ArrayList;

public record FilterCats(ArrayList<String> colors, ArrayList<String> breeds, String requesterUsername) {
}
