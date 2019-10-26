function init()
    create_key('temp_1')
    create_key('temp_2')
    set_key('temp_1', '0')
    set_key('temp_2', '0')
    create_key('temp_empty')
end

function set_new(value)
    set_key('temp_1', tostring(value))
end

function get_new()
    local val = get_key('temp_1')
    set_key('temp_2', tostring(val))
end

function get_empty_2()
    local val = get_key('temp_empty_2')
    set_key('temp_2', tostring(val))
end

function get_empty()
    local val = get_key('temp_empty')
    set_key('temp_empty', tostring(val))
end

function set_empty_string()
    set_key('temp_empty', '')
    local val = get_key('temp_empty')
    set_key('temp_empty', tostring(val))
end