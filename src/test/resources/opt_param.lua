function init()
    create_key('temp_1')
    create_key('temp_2')
    set_key('temp_1', '0')
    set_key('temp_2', '0')
end

function add()
    local num = get_key('temp_1')
    local sum = tonumber(num) + 1
    set_key('temp_1', tostring(sum))
end

function save(intInput, strInput, boolInput)
    if boolInput then
        set_key('temp_1', strInput)
    else
        set_key('temp_2', tostring(intInput))
    end
end
